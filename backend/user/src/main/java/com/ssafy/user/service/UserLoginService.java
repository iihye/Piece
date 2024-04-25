package com.ssafy.user.service;


import com.ssafy.user.dto.request.UserLoginRequestDto;
import com.ssafy.user.repository.UsersRepository;
import com.ssafy.user.global.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

        // 이 부분 임시로 컴파일 에러만 막아둠
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}




