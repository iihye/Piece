package com.ssafy.user.service;


import com.ssafy.user.dto.request.UserLoginRequestDto;
import com.ssafy.user.entity.Users;
import com.ssafy.user.repository.UsersRepository;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Long login(UserLoginRequestDto loginRequest) {
        // 사용자 정보 조회
        Optional<Users> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (!userOptional.isPresent()) {
            // 사용자를 찾을 수 없으면 null 반환
            return null;
        }

        Users user = userOptional.get();

        // 비밀번호 검증
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // 비밀번호가 일치하면 사용자의 ID 반환
            return user.getUserId();
        }
        // 비밀번호가 일치하지 않으면 null 반환
        return null;
    }
}
