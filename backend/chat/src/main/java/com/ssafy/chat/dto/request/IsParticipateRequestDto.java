package com.ssafy.chat.dto.request;

import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsParticipateRequestDto {

    Boolean isPersonal;
}
