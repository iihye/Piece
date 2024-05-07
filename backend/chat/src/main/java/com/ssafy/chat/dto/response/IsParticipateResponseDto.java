package com.ssafy.chat.dto.response;

import lombok.Builder;

@Builder
public class IsParticipateResponseDto {

    private Long chatRoomId;

//    private Cultures culture;

    private String chatRoomName;

    private Boolean isOpened;

    private Boolean isPersonal;
}
