package com.ssafy.piece.domain.chats.controller;

import com.ssafy.piece.domain.chats.dto.request.ChatRoomsRequestDto;
import com.ssafy.piece.domain.chats.entity.ChatRooms;
import com.ssafy.piece.domain.chats.service.ChatRoomsService;
import com.ssafy.piece.domain.chats.service.MongoDBChatRoomsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomsController {

    private final ChatRoomsService chatRoomService;
    private final MongoDBChatRoomsService mongoDBChatRoomsService;

    // 채팅방 생성
    @PostMapping("/chatrooms/create")
    public ResponseEntity chatRoomCreate(@RequestBody ChatRoomsRequestDto chatRoomRequestDto) {
        System.out.println("채팅방 생성 요청이 있습니다.");

        ChatRooms createdChatRoom = chatRoomService.createChatRoom(chatRoomRequestDto);
        mongoDBChatRoomsService.createMongoDBChatRoom(
            createdChatRoom.getChatRoomId());

        return ResponseEntity.ok(createdChatRoom);
    }
}
