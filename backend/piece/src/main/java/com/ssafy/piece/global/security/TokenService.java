package com.ssafy.piece.global.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService { //토큰 갱신 로직

    private JwtTokenProvider tokenProvider;

    public String refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token is invalid");
        }

        String username = tokenProvider.getUsernameFromJWT(refreshToken);
        return tokenProvider.generateToken(username);  // 새로운 엑세스 토큰 생성
    }
}
