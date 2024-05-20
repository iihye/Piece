package com.ssafy.chat.service;

import com.ssafy.chat.dto.request.ChatsRequestDto;
import com.ssafy.chat.entity.MongoDBChats;
import com.ssafy.chat.repository.MongoDBChatsRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    public List<MongoDBChats> listMessageByCount(ChatsRequestDto chatsRequestDto){
        List<MongoDBChats> listMessageResult= mongoDBChatsRepository.findTopNByChatRoomIdOrderByCreatedAtDesc(chatsRequestDto.getChatRoomId(), PageRequest.of(0, chatsRequestDto.getCount()));
        Collections.reverse(listMessageResult);

        return listMessageResult;
    };
}
