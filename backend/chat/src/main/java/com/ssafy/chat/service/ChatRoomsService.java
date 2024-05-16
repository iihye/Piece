package com.ssafy.chat.service;

import com.ssafy.chat.dto.request.OpenChatRoomsCreateRequestDto;
import com.ssafy.chat.dto.request.PersonalChatRoomsCreateRequestDto;
import com.ssafy.chat.entity.ChatRooms;
import com.ssafy.chat.exception.AlreadyCreatedChatRoomException;
import com.ssafy.chat.exception.AlreadyCreatedPersonalChatRoomException;
import com.ssafy.chat.repository.ChatRoomsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomsService {

    private final ChatRoomsRepository chatRoomRepository;

    public ChatRooms createPersonalChatRoom(Long userId, PersonalChatRoomsCreateRequestDto personalChatRoomsCreateRequestDto){
        // 이미 모두가 참여하고 있는 1:1 방이라면 생성하지 않는다.
        Long AlreadyCreatedPersonalChatRoomId=chatRoomRepository.existsPersonalChatRoomByParticipants(userId,
                personalChatRoomsCreateRequestDto.getPartnerId());

        log.info("이미 생성되어 있는 1:1?:"+AlreadyCreatedPersonalChatRoomId);

        if(AlreadyCreatedPersonalChatRoomId>0){
            throw new AlreadyCreatedPersonalChatRoomException(AlreadyCreatedPersonalChatRoomId);
        }

        ChatRooms chatRooms = ChatRooms.builder()
            .chatRoomName(personalChatRoomsCreateRequestDto.getChatRoomName())
            .isOpened(true)
            .isPersonal(true)
            .build();

        return chatRoomRepository.save(chatRooms);
    }
    public ChatRooms createOpenChatRoom(OpenChatRoomsCreateRequestDto openChatRoomsCreateRequestDto){
        // 이미 있는 오픈 방이라면 생성하지 않는다.
        if(chatRoomRepository.existsByCultureId(openChatRoomsCreateRequestDto.getCultureId())){
            throw new AlreadyCreatedChatRoomException();
        }

            ChatRooms chatRooms = ChatRooms.builder()
            .cultureId(openChatRoomsCreateRequestDto.getCultureId())
            .chatRoomName(openChatRoomsCreateRequestDto.getChatRoomName())
            .isOpened(true)
            .isPersonal(false)
            .build();

        return chatRoomRepository.save(chatRooms);
    }

    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }

}
