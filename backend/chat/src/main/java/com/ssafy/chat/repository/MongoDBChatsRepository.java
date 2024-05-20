package com.ssafy.chat.repository;

import com.ssafy.chat.entity.MongoDBChats;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBChatsRepository extends MongoRepository<MongoDBChats, Long> {

    List<MongoDBChats> findByChatRoomId(Long chatRoomId);

    List<MongoDBChats> findTopNByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId, PageRequest pageRequest);
}
