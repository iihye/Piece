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
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRegistrationRequestDto requestDto) {
        userRegistrationService.register(requestDto);
        return SuccessResponse.createSuccess(SuccessCode.JOIN_SUCCESS);
    }


    //GPT가 알려준내용... ->예외처리를 하기 위해 사용한다.(잘 모르겠어요...)
    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<Object> handleDuplicatedEmail(DuplicatedEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DuplicatedNicknameException.class)
    public ResponseEntity<Object> handleDuplicatedNickname(DuplicatedNicknameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }


    @GetMapping("/check-email")
    public ResponseEntity<Object> checkEmail(@RequestParam String email) {
        boolean isAvailable = userRegistrationService.checkEmailAvailable(email);
        if (isAvailable) {
            return SuccessResponse.createSuccess(SuccessCode.CHECK_EMAIL_GOOD);
        } else {
            return SuccessResponse.createSuccess(SuccessCode.CHECK_EMAIL_BAD);
        }
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<Object> checkNickname(@RequestParam String nickname) {
        boolean isAvailable = userRegistrationService.checkNicknameAvailable(nickname);
        if (isAvailable) {
            return SuccessResponse.createSuccess(SuccessCode.CHECK_NICKNAME_GOOD);
        } else {
            return SuccessResponse.createSuccess(SuccessCode.CHECK_NICKNAME_BAD);
        }
    }



    }


