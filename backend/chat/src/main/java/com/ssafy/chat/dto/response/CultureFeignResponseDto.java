package com.ssafy.chat.dto.response;

import com.ssafy.chat.global.response.code.ResponseCode;
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
public class CultureFeignResponseDto {
    private String code;
    private String message;
    private CultureResponseDto data;
}
