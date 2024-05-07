package com.ssafy.chat.service;

import com.ssafy.chat.dto.request.ChatRoomsLeaveRequestDto;
import com.ssafy.chat.dto.request.IsParticipateRequestDto;
import com.ssafy.chat.dto.request.ParticipantsRequestDto;
import com.ssafy.chat.dto.response.ParticipantsResponseDto;
import com.ssafy.chat.entity.ChatRooms;
import com.ssafy.chat.entity.Participants;
import com.ssafy.chat.entity.ParticipantsId;
import com.ssafy.chat.exception.AlreadyParticipatedException;
import com.ssafy.chat.repository.ChatRoomsRepository;
import com.ssafy.chat.repository.MongoDBChatRoomsRepository;
import com.ssafy.chat.repository.MongoDBChatsRepository;
import com.ssafy.chat.repository.ParticipantsRepository;
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

    public ParticipantsResponseDto saveParticipants(ParticipantsRequestDto participantRequestDto,
        Long userId) {
        ParticipantsId participantsId = new ParticipantsId(
            participantRequestDto.getChatRoomId(),
            userId
        );

        // 중복 체크
        selectParticipants(participantRequestDto, userId);

        Participants participants = participantsRepository.save(Participants.builder()
            .participantsId(participantsId)
            .build());

        ParticipantsResponseDto participantResponseDto = ParticipantsResponseDto.builder()
            .chatRoomId(participantRequestDto.getChatRoomId())
            .userId(userId)
            .createdAt(participants.getCreatedAt())
            .build();

        return participantResponseDto;
    }

    public List<ChatRooms> selectChatRoom(IsParticipateRequestDto isParticipateRequestDto,
        Long userId) {
        return participantsRepository.findIsPersonalChatRoomsByUserId(
            userId, isParticipateRequestDto.getIsPersonal());
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

    public void selectParticipants(ParticipantsRequestDto participantsRequestDto, Long userId) {
        Optional<Participants> participants = participantsRepository.findById(
            ParticipantsId.builder().chatroomId(participantsRequestDto.getChatRoomId())
                .userId(userId)
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
