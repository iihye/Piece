package com.ssafy.piece.domain.chats.controller;

import com.ssafy.piece.domain.chats.dto.request.ChatRoomsLeaveRequestDto;
import com.ssafy.piece.domain.chats.dto.request.IsParticipateRequestDto;
import com.ssafy.piece.domain.chats.dto.request.ParticipantsRequestDto;
import com.ssafy.piece.domain.chats.service.ParticipantsService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParticipantsController {

    private final ParticipantsService participantsService;

    // 채팅방 참여 처리
    @PostMapping("/participate")
    public ResponseEntity chatRoomParticipate(
        @RequestBody ParticipantsRequestDto participantRequestDto) {
        return SuccessResponse.createSuccess(SuccessCode.PARTICIPATE_SUCCESS,
            participantsService.saveParticipants(participantRequestDto));
    }

    // 내가 참여한 채팅방 조회
    @PostMapping("/isparticipate")
    public ResponseEntity chatRoomSelect(
        @RequestBody IsParticipateRequestDto isParticipateRequestDto) {
        return SuccessResponse.createSuccess(SuccessCode.PARTICIPATED_LIST_SUCCESS,
            participantsService.selectChatRoom(isParticipateRequestDto));
    }

    // 채팅방 퇴장
    @PostMapping("/leave")
    public ResponseEntity chatRoomLeave(
        @RequestBody ChatRoomsLeaveRequestDto chatRoomsLeaveRequestDto) {
        System.out.println("채팅방 퇴장 요청이 있습니다." + chatRoomsLeaveRequestDto);
        participantsService.leaveChatRoom(chatRoomsLeaveRequestDto);
        return SuccessResponse.createSuccess(SuccessCode.LEAVE_CHATROOM_SUCCESS);
    }

    // 채팅방 참여 인원 수 조회
    @GetMapping("/participate/count/{chatRoomId}")
    public ResponseEntity participantsCount(@PathVariable Long chatRoomId) {
        return SuccessResponse.createSuccess(SuccessCode.PARTICIPANT_COUNT_SUCCESS,
            participantsService.countParticipants(chatRoomId));
    }
}
