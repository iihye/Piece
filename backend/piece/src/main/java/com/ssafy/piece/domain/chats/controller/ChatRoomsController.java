package com.ssafy.piece.domain.chats.controller;

import com.ssafy.piece.domain.chats.dto.request.ChatRoomsRequestDto;
import com.ssafy.piece.domain.chats.entity.ChatRooms;
import com.ssafy.piece.domain.chats.service.ChatRoomsService;
import com.ssafy.piece.domain.chats.service.MongoDBChatRoomsService;
import com.ssafy.piece.domain.chats.service.ParticipantsService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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

        return SuccessResponse.createSuccess(SuccessCode.CHATROOM_CREATED);
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
