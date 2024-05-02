package com.ssafy.piece.domain.chats.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class ChatRoomsResponseDto {

    Long chatRoomId;
    Long cultureId;
    String chatRoomName;
    Boolean isOpened;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Boolean isPersonal;
}
