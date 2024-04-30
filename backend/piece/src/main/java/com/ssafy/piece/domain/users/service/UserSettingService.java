package com.ssafy.piece.domain.users.service;


import com.ssafy.piece.domain.users.dto.request.UserNicknameChangeRequestDto;
import com.ssafy.piece.domain.users.dto.request.UserPasswordChangeRequestDto;
import com.ssafy.piece.domain.users.entity.Users;
import com.ssafy.piece.domain.users.repository.UsersRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean changeNickname(Long userId, UserNicknameChangeRequestDto requestDto) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            // 중복 닉네임 체크
            if(userRepository.existsByNickname(requestDto.getNewNickname())) {
                // 중복된 경우 처리
                return false;
            }

            Users user = userOptional.get();
            user.setNickname(requestDto.getNewNickname());
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public boolean changePassword(Long userId, UserPasswordChangeRequestDto requestDto) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            String newPasswordEncoded = passwordEncoder.encode(requestDto.getNewPassword());
            user.setPassword(newPasswordEncoded);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
