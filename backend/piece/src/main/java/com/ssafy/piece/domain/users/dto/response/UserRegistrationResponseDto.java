package com.ssafy.piece.domain.users.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRegistrationResponseDto {
    private Long userId; //사용자의 고유 식별자
    private String message = "회원가입 성공!";
}
