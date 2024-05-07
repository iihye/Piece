package com.ssafy.chat.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class IsParticipateRequestDto {

    Boolean isPersonal;
}
