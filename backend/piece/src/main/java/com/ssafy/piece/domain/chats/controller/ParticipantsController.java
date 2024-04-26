package com.ssafy.piece.domain.chats.controller;

import com.ssafy.piece.domain.chats.dto.request.IsParticipateRequestDto;
import com.ssafy.piece.domain.chats.dto.request.ParticipantsRequestDto;
import com.ssafy.piece.domain.chats.service.ParticipantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParticipantsController {

    private final ParticipantsService participantsService;

    // 채팅방 참여 처리
    @PostMapping("/participate")
    public ResponseEntity participateChatRoom(
        @RequestBody ParticipantsRequestDto participantRequestDto) {

        return ResponseEntity.ok(participantsService.saveParticipants(participantRequestDto));
    }

    // 내가 참여한 채팅방 조회
    @PostMapping("/isparticipate")
    public ResponseEntity selectChatRoom(
        @RequestBody IsParticipateRequestDto isParticipateRequestDto) {
        System.out.println("참여 채팅방 조회 요청이 있습니다." + isParticipateRequestDto);
        return ResponseEntity.ok(participantsService.selectChatRoom(isParticipateRequestDto));
    }
}
