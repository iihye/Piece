package com.ssafy.piece.domain.chats.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class IsParticipateRequestDto {

    Long userId;
    Boolean isPersonal;
}
