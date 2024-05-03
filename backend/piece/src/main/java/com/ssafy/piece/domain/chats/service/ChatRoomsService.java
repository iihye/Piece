package com.ssafy.piece.domain.chats.service;

import com.ssafy.piece.domain.chats.dto.request.ChatRoomsRequestDto;
import com.ssafy.piece.domain.chats.entity.ChatRooms;
import com.ssafy.piece.domain.chats.exception.AlreadyCreatedChatRoomException;
import com.ssafy.piece.domain.chats.repository.ChatRoomsRepository;
import com.ssafy.piece.domain.cultures.repository.CulturesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomsService {

    private final CulturesRepository culturesRepository;
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
                .culture(culturesRepository.findById(chatRoomRequestDto.getCultureId()).get())
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
