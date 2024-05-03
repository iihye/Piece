package com.ssafy.piece.domain.chats.dto.response;

import com.ssafy.piece.domain.cultures.entity.Cultures;
import lombok.Builder;

@Builder
public class IsParticipateResponseDto {

    private Long chatRoomId;

    private Cultures culture;

    private String chatRoomName;

    private Boolean isOpened;

    private Boolean isPersonal;
}
