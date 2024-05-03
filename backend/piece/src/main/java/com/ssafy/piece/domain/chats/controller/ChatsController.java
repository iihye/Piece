package com.ssafy.piece.domain.chats.controller;

import com.ssafy.piece.domain.chats.dto.request.ChatsRequestDto;
import com.ssafy.piece.domain.chats.entity.MongoDBChats;
import com.ssafy.piece.domain.chats.service.MongoDBChatsService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatsController {

    private final SimpMessageSendingOperations template;
    private final MongoDBChatsService mongoDBChatsService;

    @MessageMapping("/chats/{chatRoomId}") // Send Destination Queue
    public void messageSend(@RequestBody MongoDBChats mongoDBChat) {
        template.convertAndSend("/sub/" + mongoDBChat.getChatRoomId(), mongoDBChat);
        mongoDBChatsService.saveMongoDBChat(mongoDBChat);
    }

    @GetMapping("/chats/list/{chatRoomId}")
    public ResponseEntity messageList(@PathVariable Long chatRoomId) {
        return SuccessResponse.createSuccess(SuccessCode.CHATMESSAGE_LIST_SUCCESS,
            mongoDBChatsService.listMongoDBChat(chatRoomId));
    }

    @PostMapping("/chats/list")
    public ResponseEntity messageListByCount(@RequestBody ChatsRequestDto chatsRequestDto) {
        return SuccessResponse.createSuccess(SuccessCode.CHATMESSAGE_N_LIST_SUCCESS,
            mongoDBChatsService.listMessageByCount(chatsRequestDto));
    }
}
