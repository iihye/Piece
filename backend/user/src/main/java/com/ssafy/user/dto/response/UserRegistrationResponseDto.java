package com.ssafy.user.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRegistrationResponseDto {
    private final Long userId;
    private final String message ;//회원가입 성공 or 실패 메시지
}
