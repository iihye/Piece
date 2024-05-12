package com.ssafy.piece.domain.users.service;


import com.ssafy.piece.domain.users.dto.request.UserNicknameChangeRequestDto;
import com.ssafy.piece.domain.users.dto.request.UserPasswordChangeRequestDto;
import com.ssafy.piece.domain.users.entity.Users;
import com.ssafy.piece.domain.users.repository.UsersRepository;
import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.code.ResponseCode;
import com.ssafy.piece.global.response.code.SuccessCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseCode changeNickname(Long userId, UserNicknameChangeRequestDto requestDto) {
        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userRepository.existsByNickname(requestDto.getNewNickname())) {
            return SuccessCode.CHECK_NICKNAME_BAD;
        }

        user.setNickname(requestDto.getNewNickname());
        userRepository.save(user);
        return SuccessCode.NICKNAME_UPDATE_SUCCESS;
    }


    public ResponseCode changePassword(Long userId, UserPasswordChangeRequestDto requestDto) {
        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())) {
            return ErrorCode.DUPLICATED_PASSWORD;
        }

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
        return SuccessCode.PASSWORD_UPDATE_SUCCESS;
    }
}
