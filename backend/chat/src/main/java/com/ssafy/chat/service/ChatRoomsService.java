package com.ssafy.chat.service;

import com.ssafy.chat.dto.request.ChatRoomsRequestDto;
import com.ssafy.chat.entity.ChatRooms;
import com.ssafy.chat.exception.AlreadyCreatedChatRoomException;
import com.ssafy.chat.repository.ChatRoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomsService {

    private final ChatRoomsRepository chatRoomRepository;

    public ChatRooms createChatRoom(ChatRoomsRequestDto chatRoomRequestDto) {
        if (chatRoomRepository.findByCultureIdAndIsPersonal(chatRoomRequestDto.getCultureId(),
            chatRoomRequestDto.getIsPersonal()).isPresent()) {
            throw new AlreadyCreatedChatRoomException();
        }

        ChatRooms chatRooms;

        System.out.println("culture id:" + chatRoomRequestDto.getCultureId());

        if (chatRoomRequestDto.getCultureId() == null) { // 사설 채팅방
            chatRooms = ChatRooms.builder()
                .chatRoomName(chatRoomRequestDto.getChatRoomName())
                .isOpened(true)
                .isPersonal(chatRoomRequestDto.getIsPersonal())
                .build();
        } else { // culture 채팅방
            chatRooms = ChatRooms.builder()
                .cultureId(chatRoomRequestDto.getCultureId())
                .chatRoomName(chatRoomRequestDto.getChatRoomName())
                .isOpened(true)
                .isPersonal(chatRoomRequestDto.getIsPersonal())
                .build();
        }

        return chatRoomRepository.save(chatRooms);
    }

    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }

}
