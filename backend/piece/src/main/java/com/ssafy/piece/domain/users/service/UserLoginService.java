package com.ssafy.piece.domain.users.service;


import com.ssafy.piece.domain.users.dto.request.UserLoginRequestDto;
import com.ssafy.piece.domain.users.repository.UsersRepository;
import com.ssafy.piece.global.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLoginService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager; // 스프링 시큐리티 인증 관리자
    private final JwtTokenProvider tokenProvider;


    public String login(UserLoginRequestDto loginRequest) {
        try {
            // 사용자 인증 시도
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
            if (authentication.isAuthenticated()) {
                return tokenProvider.generateToken(String.valueOf(authentication));
            }
            throw new RuntimeException("인증 실패");

    }
}




