package com.ssafy.piece.domain.chats.service;

import com.ssafy.piece.domain.chats.entity.MongoDBChatRooms;
import com.ssafy.piece.domain.chats.repository.MongoDBChatRoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoDBChatRoomsService {

    private final MongoDBChatRoomsRepository mongoDBChatRoomsRepository;

    public void createMongoDBChatRoom(Long chatRoomId) {
        MongoDBChatRooms mongoDBChatRoom =
            MongoDBChatRooms.builder()
                .chatRoomId(chatRoomId)
                .build();

        mongoDBChatRoomsRepository.save(mongoDBChatRoom);
    }

}
