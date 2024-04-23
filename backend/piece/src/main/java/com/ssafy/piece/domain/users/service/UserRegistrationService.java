package com.ssafy.piece.domain.users.service;


import com.ssafy.piece.domain.users.dto.request.UserRegistrationRequestDto;
import com.ssafy.piece.domain.users.entity.Users;
import com.ssafy.piece.domain.users.repository.UsersRepository;
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
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화를 담당하는 컴포넌트


    /**
     * 사용자의 일반 회원가입을 처리합니다. 입력된 사용자 데이터를 검증하고, 새로운 사용자를 데이터베이스에 저장합니다.
     *
     * @param requestDto 사용자로부터 받은 회원가입 데이터를 담은 DTO
     * @return Users 새로 생성된 사용자 엔티티
     * @throws IllegalStateException 만약 이메일이 이미 사용 중일 경우 예외를 발생시킵니다.
     */
    public Users register(UserRegistrationRequestDto requestDto) { //이메일 중복검사
        if (usersRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        if (usersRepository.existsByNickname(requestDto.getNickname())) { //닉네임 중복검사
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }

        // 비밀번호를 해싱하여 안전하게 저장
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // Users 엔티티를 생성하고 초기화
        Users newUser = Users.builder()
            .email(requestDto.getEmail())
            .username(requestDto.getUsername())
            .password(encodedPassword)
            .nickname(requestDto.getNickname())
            .profileImage(requestDto.getProfileImage()) //프로필 이미지는 선택 사항입니다.
            .build();

        // 생성된 사용자 정보를 데이터베이스에 저장
        return usersRepository.save(newUser);
    }
}
