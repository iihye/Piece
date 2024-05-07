package com.ssafy.chat.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomsRequestDto {

    Long cultureId;
    String chatRoomName;
    Boolean isPersonal;
}