package com.ssafy.piece.domain.chats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomsService {
//
//    private final CulturesRepository culturesRepository;
//    private final ChatRoomsRepository chatRoomRepository;
//
//    public ChatRooms createChatRoom(ChatRoomsRequestDto chatRoomRequestDto) {
//        System.out.println("culture id:" + chatRoomRequestDto.getCultureId());
//        ChatRooms chatRooms = ChatRooms.builder()
//            .culture(culturesRepository.findById(chatRoomRequestDto.getCultureId()).get())
//            .chatRoomName(chatRoomRequestDto.getChatRoomName())
//            .isOpened(true)
//            .isPersonal(chatRoomRequestDto.getIsPersonal())
//            .build();
//
//        return chatRoomRepository.save(chatRooms);
//    }
}
