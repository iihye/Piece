package com.ssafy.piece.domain.chats.repository;

import com.ssafy.piece.domain.chats.entity.MongoDBChats;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBChatsRepository extends MongoRepository<MongoDBChats, Long> {

    List<MongoDBChats> findByChatRoomId(Long chatRoomId);

    List<MongoDBChats> findTopNByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId, PageRequest pageRequest);
}
