package com.ssafy.piece.domain.users.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDto {
    private String userId; // 사용자의 고유 식별자
    private String username; // 사용자의 로그인 아이디

    //아래 세가지는 아직 어려워서 나중에 구현하겠습니다...
//    private String accessToken; // 로그인 성공 시 발급되는 JWT 토큰
//    private String tokenType = "Bearer"; // 토큰 타입 (일반적으로 Bearer)?
//    private Long expiresIn; // 토큰의 만료 시간 (예: 3600은 3600초)
}
