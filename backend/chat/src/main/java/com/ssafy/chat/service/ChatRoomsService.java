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
        // 예외처리 당장 하지않음. 근데 오픈채팅일 경우 중복으로 생성되는 문제 생길거임
//        if (chatRoomRepository.findByCultureIdAndIsPersonal(chatRoomRequestDto.getCultureId(),
//            chatRoomRequestDto.getIsPersonal()).isPresent()) {
//            throw new AlreadyCreatedChatRoomException();
//        }

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
