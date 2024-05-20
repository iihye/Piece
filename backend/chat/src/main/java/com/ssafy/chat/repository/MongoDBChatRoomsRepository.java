package com.ssafy.chat.repository;

import com.ssafy.chat.entity.MongoDBChatRooms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBChatRoomsRepository extends MongoRepository<MongoDBChatRooms, Long> {

    void deleteByChatRoomId(Long chatRoomId);
}
