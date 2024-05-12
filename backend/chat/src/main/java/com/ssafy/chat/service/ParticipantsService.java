package com.ssafy.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantsService {
//
//    private final ParticipantsRepository participantsRepository;
//
//    public ParticipantsResponseDto saveParticipants(ParticipantsRequestDto participantRequestDto) {
//        ParticipantsId participantsId = new ParticipantsId(
//            participantRequestDto.getChatRoomId(),
//            participantRequestDto.getUserId()
//        );
//        Participants participants = Participants.builder()
//            .participantsId(participantsId)
//            .build();
//
//        Participants participants1 = participantsRepository.save(participants);
//
//        ParticipantsResponseDto participantResponseDto = ParticipantsResponseDto.builder()
//            .chatRoomId(participantRequestDto.getChatRoomId())
//            .userId(participantRequestDto.getUserId())
//            .createdAt(participants1.getCreatedAt())
//            .build();
//
//        return participantResponseDto;
//    }
}
