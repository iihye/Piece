package com.ssafy.piece.domain.users.service;


import com.ssafy.piece.domain.users.dto.request.UserLoginRequestDto;
import com.ssafy.piece.domain.users.entity.Users;
import com.ssafy.piece.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean login(UserLoginRequestDto loginRequest) {
        // 사용자 정보 조회
        Users user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            // 사용자를 찾을 수 없으면 false 반환
            return false;
        }
        // 비밀번호 검증
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // 비밀번호가 일치하면 true 반환
            return true;
        }
        // 비밀번호가 일치하지 않으면 false 반환
        return false;
    }
}


