package com.ssafy.piece.domain.chats.service;

import com.ssafy.piece.domain.chats.dto.request.ChatRoomsLeaveRequestDto;
import com.ssafy.piece.domain.chats.dto.request.IsParticipateRequestDto;
import com.ssafy.piece.domain.chats.dto.request.ParticipantsRequestDto;
import com.ssafy.piece.domain.chats.dto.response.ParticipantsResponseDto;
import com.ssafy.piece.domain.chats.entity.ChatRooms;
import com.ssafy.piece.domain.chats.entity.Participants;
import com.ssafy.piece.domain.chats.entity.ParticipantsId;
import com.ssafy.piece.domain.chats.exception.AlreadyParticipatedException;
import com.ssafy.piece.domain.chats.repository.ChatRoomsRepository;
import com.ssafy.piece.domain.chats.repository.MongoDBChatRoomsRepository;
import com.ssafy.piece.domain.chats.repository.MongoDBChatsRepository;
import com.ssafy.piece.domain.chats.repository.ParticipantsRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantsService {

    private final ParticipantsRepository participantsRepository;
    private final ChatRoomsRepository chatRoomsRepository;
    private final MongoDBChatRoomsRepository mongoDBChatRoomsRepository;
    private final MongoDBChatsRepository mongoDBChatsRepository;

    public ParticipantsResponseDto saveParticipants(ParticipantsRequestDto participantRequestDto) {
        ParticipantsId participantsId = new ParticipantsId(
            participantRequestDto.getChatRoomId(),
            participantRequestDto.getUserId()
        );

        // 중복 체크
        selectParticipants(participantRequestDto);

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

    public List<ChatRooms> selectChatRoom(IsParticipateRequestDto isParticipateRequestDto) {
        return participantsRepository.findIsPersonalChatRoomsByUserId(
            isParticipateRequestDto.getUserId(), isParticipateRequestDto.getIsPersonal());
    }

    public void leaveChatRoom(ChatRoomsLeaveRequestDto chatRoomsLeaveRequestDto) {
        ParticipantsId participantsId = ParticipantsId.builder()
            .chatroomId(chatRoomsLeaveRequestDto.getChatRoomId())
            .userId(chatRoomsLeaveRequestDto.getUserId())
            .build();

        if (chatRoomsLeaveRequestDto.getIsPersonal()) { // 개인 채팅방
            if (chatRoomsRepository.findIsOpenedByChatRoomId(
                chatRoomsLeaveRequestDto.getChatRoomId()) == true) { // 2명 참가했을 때 1명 나가기
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

    public void selectParticipants(ParticipantsRequestDto participantsRequestDto) {
        Optional<Participants> participants = participantsRepository.findById(
            ParticipantsId.builder().chatroomId(participantsRequestDto.getChatRoomId())
                .userId(participantsRequestDto.getUserId())
                .build());

        System.out.println(participants);

        if (participants.isPresent()) {
            throw new AlreadyParticipatedException();
        }
    }

    public void leaveChatRoomAll(Long chatroomId) {
        participantsRepository.deleteByParticipantsId_ChatroomId(chatroomId);
    }
}
