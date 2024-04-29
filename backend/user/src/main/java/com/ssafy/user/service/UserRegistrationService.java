package com.ssafy.user.service;


import com.ssafy.user.dto.request.UserRegistrationRequestDto;
import com.ssafy.user.entity.Users;
import com.ssafy.user.exception.DuplicatedEmailException;
import com.ssafy.user.exception.DuplicatedNicknameException;
import com.ssafy.user.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 일반 회원가입 서비스입니다.
 * 회원가입시 데이터베이스에 저장합니다.
 * */
@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화


    /**
     * 사용자의 일반 회원가입을 처리합니다. 새로운 사용자를 데이터베이스에 저장합니다.
     */
    public Users register(UserRegistrationRequestDto requestDto) { //이메일 중복검사
        if (usersRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicatedEmailException();
        }

        if (usersRepository.existsByNickname(requestDto.getNickname())) { //닉네임 중복검사
            throw new DuplicatedNicknameException();
        }

        // 비밀번호를 해싱하여 안전하게 저장
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Users newUser = Users.builder()
            .email(requestDto.getEmail())
            .username(requestDto.getUsername())
            .password(encodedPassword)
            .nickname(requestDto.getNickname())
            .profileImage(requestDto.getProfileImage()) //선택사항
            .build();

        // 생성된 사용자 정보를 데이터베이스에 저장
        return usersRepository.save(newUser);
    }


    //입력된 이메일이 존재하는지 확인
    public boolean checkEmailAvailable(String email) {
        return !usersRepository.existsByEmail(email);
    }


    //입력된 닉네임이 존재하는지 확인
    public boolean checkNicknameAvailable(String nickname) {
        return !usersRepository.existsByNickname(nickname);
    }
}
