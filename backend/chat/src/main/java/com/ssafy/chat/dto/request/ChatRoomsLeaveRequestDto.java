package com.ssafy.chat.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatRoomsLeaveRequestDto {

    Long chatRoomId;
    Boolean isPersonal;
}
