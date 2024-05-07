package com.ssafy.chat.service;

import com.ssafy.chat.entity.MongoDBChatRooms;
import com.ssafy.chat.repository.MongoDBChatRoomsRepository;
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

    public void deleteMongoDBChatRoom(long chatRoomId) {
        mongoDBChatRoomsRepository.deleteByChatRoomId(chatRoomId);
    }
}
