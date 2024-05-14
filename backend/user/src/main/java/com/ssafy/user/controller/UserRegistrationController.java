package com.ssafy.user.controller;

import com.ssafy.user.dto.request.UserRegistrationRequestDto;
import com.ssafy.user.global.response.code.SuccessCode;
import com.ssafy.user.global.response.structure.SuccessResponse;
import com.ssafy.user.service.UserRegistrationService;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<Object> addUser(@RequestBody UserRegistrationRequestDto registrationDto) {
        userRegistrationService.register(registrationDto);
        return ResponseEntity.ok(SuccessResponse.createSuccess(SuccessCode.JOIN_SUCCESS));
    }

    @PostMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestBody String nickname) {
        boolean isAvailable = userRegistrationService.checkNicknameAvailable(nickname);
        if (isAvailable) {
            return ResponseEntity.ok(SuccessResponse.createSuccess(SuccessCode.CHECK_NICKNAME_GOOD));
        } else {
            return ResponseEntity.ok(SuccessResponse.createSuccess(SuccessCode.CHECK_NICKNAME_BAD));
        }
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody String email) {
        boolean isAvailable = userRegistrationService.checkEmailAvailable(email);
        if (isAvailable) {
            return ResponseEntity.ok(SuccessResponse.createSuccess(SuccessCode.CHECK_EMAIL_GOOD));
        } else {
            return ResponseEntity.ok(SuccessResponse.createSuccess(SuccessCode.CHECK_EMAIL_BAD));
        }
    }







}
