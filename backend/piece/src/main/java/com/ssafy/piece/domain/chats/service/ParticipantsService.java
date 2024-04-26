package com.ssafy.piece.domain.chats.service;

import com.ssafy.piece.domain.chats.dto.request.IsParticipateRequestDto;
import com.ssafy.piece.domain.chats.dto.request.ParticipantsRequestDto;
import com.ssafy.piece.domain.chats.dto.response.ParticipantsResponseDto;
import com.ssafy.piece.domain.chats.entity.ChatRooms;
import com.ssafy.piece.domain.chats.entity.Participants;
import com.ssafy.piece.domain.chats.entity.ParticipantsId;
import com.ssafy.piece.domain.chats.repository.ParticipantsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantsService {

    private final ParticipantsRepository participantsRepository;

    public ParticipantsResponseDto saveParticipants(ParticipantsRequestDto participantRequestDto) {
        ParticipantsId participantsId = new ParticipantsId(
            participantRequestDto.getChatRoomId(),
            participantRequestDto.getUserId()
        );
        Participants participants = Participants.builder()
            .participantsId(participantsId)
            .build();

        Participants participants1 = participantsRepository.save(participants);

        ParticipantsResponseDto participantResponseDto = ParticipantsResponseDto.builder()
            .chatRoomId(participantRequestDto.getChatRoomId())
            .userId(participantRequestDto.getUserId())
            .createdAt(participants1.getCreatedAt())
            .build();

        return participantResponseDto;
    }

    public List<ChatRooms> selectChatRoom(IsParticipateRequestDto isParticipateRequestDto) {
        return participantsRepository.findIsPersonalChatRoomsByUserId(
            isParticipateRequestDto.getUserId(), isParticipateRequestDto.getIsPersonal());
    }
}
