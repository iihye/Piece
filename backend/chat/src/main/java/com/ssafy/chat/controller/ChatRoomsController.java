package com.ssafy.chat.controller;

import com.ssafy.chat.dto.request.ChatRoomsRequestDto;
import com.ssafy.chat.entity.ChatRooms;
import com.ssafy.chat.global.annotation.AuthenticatedUser;
import com.ssafy.chat.global.response.code.SuccessCode;
import com.ssafy.chat.global.response.structure.SuccessResponse;
import com.ssafy.chat.service.ChatRoomsService;
import com.ssafy.chat.service.MongoDBChatRoomsService;
import com.ssafy.chat.service.ParticipantsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatRoomsController {

    private final ChatRoomsService chatRoomService;
    private final MongoDBChatRoomsService mongoDBChatRoomsService;
    private final ParticipantsService participantsService;

    // 채팅방 생성
    @PostMapping("/chatrooms/create")
    public ResponseEntity chatRoomCreate(@RequestBody ChatRoomsRequestDto chatRoomRequestDto) {
        ChatRooms createdChatRoom = chatRoomService.createChatRoom(chatRoomRequestDto);
        mongoDBChatRoomsService.createMongoDBChatRoom(
            createdChatRoom.getChatRoomId());

        return SuccessResponse.createSuccess(SuccessCode.CHATROOM_CREATED,
            createdChatRoom.getChatRoomId());
    }

    // 채팅방 삭제
    @DeleteMapping("/chatrooms/delete/{chatRoomId}")
    public ResponseEntity chatRoomDelete(@PathVariable Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        mongoDBChatRoomsService.deleteMongoDBChatRoom(chatRoomId);
        // 해당 채팅방모든 참가 끊기
        participantsService.leaveChatRoomAll(chatRoomId);

        return SuccessResponse.createSuccess(SuccessCode.CHATROOM_DELETED);
    }
}