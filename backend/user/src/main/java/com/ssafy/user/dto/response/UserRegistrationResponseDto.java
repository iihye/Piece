package com.ssafy.user.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRegistrationResponseDto {
    private final Long userId; //회원가입을 마치면 UserId를 반환한다. 이 id를 이용하여 닉네임, 비번 변경에 이용


}
