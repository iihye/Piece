package com.ssafy.chat.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class ChatsRequestDto {
    Long chatRoomId;
    int count;
}
