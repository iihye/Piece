package com.ssafy.piece.domain.users.service;


import com.ssafy.piece.domain.users.dto.request.UserLoginRequestDto;
import com.ssafy.piece.domain.users.repository.UsersRepository;
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


    public String login(UserLoginRequestDto loginRequest) {
        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );



}
