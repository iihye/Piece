package com.ssafy.chat.service;

import com.ssafy.chat.dto.request.ChatRoomsLeaveRequestDto;
import com.ssafy.chat.dto.request.IsParticipateRequestDto;
import com.ssafy.chat.dto.request.ParticipantsRequestDto;
import com.ssafy.chat.dto.response.*;
import com.ssafy.chat.entity.ChatRooms;
import com.ssafy.chat.entity.MongoDBChats;
import com.ssafy.chat.entity.Participants;
import com.ssafy.chat.entity.ParticipantsId;
import com.ssafy.chat.exception.AlreadyParticipatedException;
import com.ssafy.chat.global.client.PieceClient;
import com.ssafy.chat.global.client.UserClient;
import com.ssafy.chat.repository.ChatRoomsRepository;
import com.ssafy.chat.repository.MongoDBChatRoomsRepository;
import com.ssafy.chat.repository.MongoDBChatsRepository;
import com.ssafy.chat.repository.ParticipantsRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantsService {

    private final ParticipantsRepository participantsRepository;
    private final ChatRoomsRepository chatRoomsRepository;
    private final MongoDBChatRoomsRepository mongoDBChatRoomsRepository;
    private final MongoDBChatsRepository mongoDBChatsRepository;

    private final PieceClient pieceClient;
    private final UserClient userClient;

    public ParticipantsResponseDto saveParticipants(ParticipantsRequestDto participantRequestDto) {
        ParticipantsId participantsId = new ParticipantsId(
            participantRequestDto.getChatRoomId(),
            participantRequestDto.getUserId()
        );

        // 1:1 채팅방인지 open 채팅방인지 체크
        Boolean isPersonal=chatRoomsRepository.findIsPersonalByChatRoomId(participantRequestDto.getChatRoomId());

        // 이미 참여한 채팅방인지 체크

        if(isPersonal){ // 1:1
            if(countParticipants(participantRequestDto.getChatRoomId())==2){
                throw new AlreadyParticipatedException();
            }
        }else{ // open
            if(participantsRepository.existsByUserIdAndChatroomId(participantRequestDto.getUserId(),
                participantRequestDto.getChatRoomId())){
                throw new AlreadyParticipatedException();
            }
        }

        Participants participants = participantsRepository.save(Participants.builder()
            .participantsId(participantsId)
            .build());

        ParticipantsResponseDto participantResponseDto = ParticipantsResponseDto.builder()
            .chatRoomId(participantRequestDto.getChatRoomId())
            .userId(participantRequestDto.getUserId())
            .createdAt(participants.getCreatedAt())
            .build();

        return participantResponseDto;
    }

    public List<IsParticipateResponseDto> selectChatRoom(IsParticipateRequestDto isParticipateRequestDto, Long userId) {
        log.info("selectChatRoom 진입");
        List<ChatRooms> chatRoomList=participantsRepository.findIsPersonalChatRoomsByUserId(
            userId, isParticipateRequestDto.getIsPersonal());

        List<IsParticipateResponseDto> selectedChatRoomList=new ArrayList<>();
        for(ChatRooms c: chatRoomList){
//            log.info("piece로부터 받아온 culture 정보:"+pieceClient.getCulture(c.getCultureId()));
            List<MongoDBChats> lastMessage=mongoDBChatsRepository.findTopNByChatRoomIdOrderByCreatedAtDesc(c.getChatRoomId(), PageRequest.of(0, 1));

            if(isParticipateRequestDto.getIsPersonal()){ // 개인 채팅방이라면
                List<Long> participantIds=participantsRepository.findUserIdsByChatroomId(c.getChatRoomId());

                List<ProcessedUserResponseDto> processedUserResponseDtos = new ArrayList<>();

                for(Long participantId:participantIds){
                    // userClient에서 user 받아오기
                    UserFeignResponseDto userFeignResponseDto = userClient.getUser(participantId);
                    UserResponseDto userResponseDto = UserResponseDto.builder()
                            .userId(userFeignResponseDto.getUserId())
                            .profileImage(userFeignResponseDto.getProfileImage())
                            .nickname(userFeignResponseDto.getNickname())
                            .labelId(userFeignResponseDto.getLabelId())
                            .build();
                    log.info("selectChatRoom userResponse: ", userResponseDto);

                    // 현재 착용한 칭호 보유 여부에 따라 분기
                    if(userResponseDto.getLabelId()!=null || userResponseDto.getLabelId() != 0){
                        ProcessedUserResponseDto processedUserResponseDto=
                            ProcessedUserResponseDto.builder()
                                .userId(userResponseDto.getUserId())
                                .title(pieceClient.getLabel(userResponseDto.getLabelId()).getData().getTitle())
                                .nickname(userResponseDto.getNickname())
                                .profileImage(userResponseDto.getProfileImage())
                                .build();

                        processedUserResponseDtos.add(processedUserResponseDto);
                    }else{
                        ProcessedUserResponseDto processedUserResponseDto=
                            ProcessedUserResponseDto.builder()
                                .userId(userResponseDto.getUserId())
                                .title("")
                                .nickname(userResponseDto.getNickname())
                                .profileImage(userResponseDto.getProfileImage())
                                .build();

                        processedUserResponseDtos.add(processedUserResponseDto);
                    }
                }

                if(!lastMessage.isEmpty()) { // 메시지 1개라도 보유
                    selectedChatRoomList.add(
                        IsParticipateResponseDto.builder()
                            .chatRoomName(c.getChatRoomName())
                            .chatRoomId(c.getChatRoomId())
                            .isPersonal(c.getIsPersonal())
                            .isOpened(c.getIsOpened())
                            .participants(processedUserResponseDtos)
                            .lastMessage(lastMessage.get(0).getContent())
                            .build()
                    );
                }else{
                    selectedChatRoomList.add(
                        IsParticipateResponseDto.builder()
                            .chatRoomName(c.getChatRoomName())
                            .chatRoomId(c.getChatRoomId())
                            .isPersonal(c.getIsPersonal())
                            .isOpened(c.getIsOpened())
                            .participants(processedUserResponseDtos)
                            .build()
                    );
                }
            }else{ // 오픈 채팅방이라면
                if(!lastMessage.isEmpty()) { // 메시지 1개라도 보유
                    System.out.println("마지막 메시지 보유:"+lastMessage.get(0).getContent());
                    selectedChatRoomList.add(
                        IsParticipateResponseDto.builder()
                            .chatRoomName(c.getChatRoomName())
                            .chatRoomId(c.getChatRoomId())
                            .isPersonal(c.getIsPersonal())
                            .isOpened(c.getIsOpened())
                            .culture(pieceClient.getCulture(c.getCultureId()).getData())
                            .participantCount(countParticipants(c.getChatRoomId()))
                            .lastMessage(lastMessage.get(0).getContent())
                            .build()
                    );
                }else{
                    selectedChatRoomList.add(
                        IsParticipateResponseDto.builder()
                            .chatRoomName(c.getChatRoomName())
                            .chatRoomId(c.getChatRoomId())
                            .isPersonal(c.getIsPersonal())
                            .isOpened(c.getIsOpened())
                            .culture(pieceClient.getCulture(c.getCultureId()).getData()) // feignclient로 얻어야함
                            .participantCount(countParticipants(c.getChatRoomId()))
                            .build()
                    );
                }
            }
        }

        return selectedChatRoomList;
    }

    public void leaveChatRoom(ChatRoomsLeaveRequestDto chatRoomsLeaveRequestDto, Long userId) {
        ParticipantsId participantsId = ParticipantsId.builder()
            .chatroomId(chatRoomsLeaveRequestDto.getChatRoomId())
            .userId(userId)
            .build();

        if (chatRoomsLeaveRequestDto.getIsPersonal()) { // 개인 채팅방
            if (chatRoomsRepository.findIsOpenedByChatRoomId(
                chatRoomsLeaveRequestDto.getChatRoomId())) { // 2명 참가했을 때 1명 나가기
                participantsRepository.deleteById(participantsId);
                chatRoomsRepository.updateChatRoomStatus(chatRoomsLeaveRequestDto.getChatRoomId());
            } else { // 1명 남았을 때 1명 나가기
                participantsRepository.deleteById(participantsId);
                // mySQL 채팅방 제거
                chatRoomsRepository.deleteById(chatRoomsLeaveRequestDto.getChatRoomId());
                // mongoDB 채팅방 제거
                mongoDBChatRoomsRepository.deleteById(chatRoomsLeaveRequestDto.getChatRoomId());
                // mongoDB 채팅 메시지 제거
                mongoDBChatsRepository.deleteById(chatRoomsLeaveRequestDto.getChatRoomId());
            }
        } else { // 오픈 채팅방
            // 참가 끊기
            participantsRepository.deleteById(participantsId);

            // 남은 채팅 참여자 수가 0명이라면 오픈 채팅방 제거
            if (participantsRepository.countByParticipantsId_ChatroomId(
                chatRoomsLeaveRequestDto.getChatRoomId()) < 1) {

                // mySQL 채팅방 제거
                chatRoomsRepository.deleteById(chatRoomsLeaveRequestDto.getChatRoomId());
                // mongoDB 채팅방 제거
                mongoDBChatRoomsRepository.deleteById(chatRoomsLeaveRequestDto.getChatRoomId());
                // mongoDB 채팅 메시지 제거
                mongoDBChatsRepository.deleteById(chatRoomsLeaveRequestDto.getChatRoomId());
            }
        }
    }

    public Long countParticipants(Long chatRoomId) {
        return participantsRepository.countByParticipantsId_ChatroomId(chatRoomId);
    }

    public void leaveChatRoomAll(Long chatroomId) {
        participantsRepository.deleteByParticipantsId_ChatroomId(chatroomId);
    }
}
