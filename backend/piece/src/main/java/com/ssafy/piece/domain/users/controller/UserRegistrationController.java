package com.ssafy.piece.domain.users.controller;


import com.ssafy.piece.domain.users.dto.request.UserRegistrationRequestDto;
import com.ssafy.piece.domain.users.dto.response.UserRegistrationResponseDto;
import com.ssafy.piece.domain.users.exception.DuplicatedEmailException;
import com.ssafy.piece.domain.users.exception.DuplicatedNicknameException;
import com.ssafy.piece.domain.users.service.UserRegistrationService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 일반 회원가입 컨트롤러입니다.
 * */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/register")
    public String register() {
        return "register.html";  // 회원가입 페이지로 이동
    }

    @PostMapping("/register") //회원가입
    public ResponseEntity<Object> addUser(@RequestBody UserRegistrationRequestDto registrationDto) {
        userRegistrationService.register(registrationDto);
        return SuccessResponse.createSuccess(SuccessCode.JOIN_SUCCESS);

    }

    @GetMapping("/login")
    public String login() {
        return "login.html";  // 로그인 페이지로
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
        // 현재 로그인한 사용자의 정보를 사용할 수 있습니다.
        return "mypage.html";  // 마이페이지로
    }

    @GetMapping("/check-nickname") //닉네임 중복체크
    public ResponseEntity<Object> checkNickname(@RequestParam String nickname) {
        boolean isAvailable = userRegistrationService.checkNicknameAvailable(nickname);
        if (isAvailable) {
            return SuccessResponse.createSuccess(SuccessCode.CHECK_NICKNAME_GOOD);
        } else {
            return SuccessResponse.createSuccess(SuccessCode.CHECK_NICKNAME_BAD);
        }
    }

    @GetMapping("/check-email") //이메일 중복 체크
    public ResponseEntity<Object> checkEmail(@RequestParam String email) {
        boolean isAvailable = userRegistrationService.checkEmailAvailable(email);
        if (isAvailable) {
            return SuccessResponse.createSuccess(SuccessCode.CHECK_EMAIL_GOOD);
        } else {
            return SuccessResponse.createSuccess(SuccessCode.CHECK_EMAIL_BAD);
        }
    }



    }


