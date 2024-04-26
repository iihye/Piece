package com.ssafy.piece.domain.chats.repository;

import com.ssafy.piece.domain.chats.entity.MongoDBChatRooms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBChatRoomsRepository extends MongoRepository<MongoDBChatRooms, Long> {

}
