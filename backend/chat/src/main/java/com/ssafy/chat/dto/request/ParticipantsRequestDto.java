package com.ssafy.chat.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantsRequestDto {

    Long chatRoomId;
    Long userId;
}
