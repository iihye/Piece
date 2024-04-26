package com.ssafy.piece.domain.chats.service;

import com.ssafy.piece.domain.chats.entity.MongoDBChats;
import com.ssafy.piece.domain.chats.repository.MongoDBChatsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoDBChatsService {

    private final MongoDBChatsRepository mongoDBChatsRepository;

    public List<MongoDBChats> listMongoDBChat(Long chatRoomId) {
        return mongoDBChatsRepository.findByChatRoomId(chatRoomId);
    }

    public void saveMongoDBChat(MongoDBChats mongoDBChat) {
        mongoDBChatsRepository.save(mongoDBChat);
    }
}
