package com.ssafy.piece.domain.chats.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomsRequestDto {

    Long cultureId;
    String chatRoomName;
    Boolean isPersonal;
}