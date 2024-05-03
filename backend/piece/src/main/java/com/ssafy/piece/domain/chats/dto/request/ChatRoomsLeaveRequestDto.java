package com.ssafy.piece.domain.chats.dto.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatRoomsLeaveRequestDto {

    Long userId;
    Long chatRoomId;
    Boolean isPersonal;
}
