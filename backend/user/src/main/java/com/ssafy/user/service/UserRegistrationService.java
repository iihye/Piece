package com.ssafy.user.service;


import com.ssafy.user.dto.request.UserRegistrationRequestDto;
import com.ssafy.user.dto.response.UserRegistrationResponseDto;
import com.ssafy.user.entity.Users;
import com.ssafy.user.exception.DuplicatedEmailException;
import com.ssafy.user.exception.DuplicatedNicknameException;
//import com.ssafy.user.global.email.EmailSender;
import com.ssafy.user.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 일반 회원가입 서비스입니다.
 * 회원가입시 데이터베이스에 저장합니다.
 * */
@Service
@AllArgsConstructor
public class    UserRegistrationService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
//    private final EmailSender emailSender;





    /**
     * 사용자의 일반 회원가입을 처리합니다. 새로운 사용자를 데이터베이스에 저장합니다.
     */
    public UserRegistrationResponseDto  register(UserRegistrationRequestDto registrationDto) {

        if (usersRepository.existsByEmail(registrationDto.getEmail())) {
            throw new DuplicatedEmailException();
        }

        if (usersRepository.existsByNickname(registrationDto.getNickname())) {
            throw new DuplicatedNicknameException();
        }


        Users newUser = Users.builder()
            .email(registrationDto.getEmail())
//            .username(registrationDto.getUse  rname())
            .password(passwordEncoder.encode(registrationDto.getPassword()))
            .nickname(registrationDto.getNickname())
//            .profileImage(registrationDto.getProfileImage())
            .isTutorial(true)  // 기본적으로 튜토리얼 보이도록 설정합니다.
            .socialId("N/A")  // 소셜 ID는 현재 설정되지 않음
            .labelId(0L)      // 칭호 ID는 임시로 0으로 설정
            .build();


        newUser = usersRepository.save(newUser); //데이터베이스에 새로운 사용자를 저장합니다.



        return new UserRegistrationResponseDto(newUser.getUserId());
    }


    //입력된 이메일이 존재하는지 확인
    public boolean checkEmailAvailable(String email) {
        return !usersRepository.existsByEmail(email);
    }


    //입력된 닉네임이 존재하는지 확인
    public boolean checkNicknameAvailable(String nickname) {
        return !usersRepository.existsByNickname(nickname);
    }

//    // 회원가입 요청을 처리하고 이메일 인증 코드를 발송
//    public String initiateRegistration(UserRegistrationRequestDto registrationDto) {
//        if (usersRepository.existsByEmail(registrationDto.getEmail())) {
//            throw new RuntimeException("이미 사용중인 이메일입니다.");
//        }
//
//        // 사용자 정보를 저장하는 로직은 인증 완료 후에 수행
//        String verificationCode = emailSender.generateCode();
//        emailSender.sendMail(registrationDto.getEmail(), "이메일 인증 번호",
//            "인증 번호를 입력해 주세요 : " + verificationCode);
//
//        // 인증 코드는 암호화하여 저장하고 클라이언트에 반환하지 않음
//        return verificationCode;  // 이 부분은 변경될 수 있음, 클라이언트에는 반환하지 않고 서버에서만 관리
//    }
//
//    // 인증 코드 검증
//    public boolean verifyEmail(String code) {
//        // 여기에서 코드 검증 로직 구현
//        return true; // 예시로 항상 true를 반환
//    }
}