package com.ssafy.user.controller;

import com.ssafy.user.dto.request.UserRegistrationRequestDto;
import com.ssafy.user.global.response.code.SuccessCode;
import com.ssafy.user.global.response.structure.SuccessResponse;
import com.ssafy.user.service.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


    @PostMapping("/register") //회원가입
    public ResponseEntity<Object> addUser(@RequestBody UserRegistrationRequestDto registrationDto) {
        userRegistrationService.register(registrationDto);
        return SuccessResponse.createSuccess(SuccessCode.JOIN_SUCCESS);

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

