package com.ssafy.piece.domain.users.controller;

import com.ssafy.piece.domain.users.dto.request.UserNicknameChangeRequestDto;
import com.ssafy.piece.domain.users.dto.request.UserPasswordChangeRequestDto;
import com.ssafy.piece.domain.users.service.UserSettingService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserSettingController {



        private final UserSettingService userSettingService;

    @PutMapping("/{userId}/nickname") // 닉네임 변경
    public ResponseEntity<Object> changeNickname(@PathVariable("userId") Long userId,
        @RequestBody UserNicknameChangeRequestDto requestDto) {
        boolean isUpdated = userSettingService.changeNickname(userId, requestDto);
        if (isUpdated) {
            return SuccessResponse.createSuccess(SuccessCode.NICKNAME_UPDATE_SUCCESS);
        } else {
            // 닉네임 변경 실패 시 적절한 에러 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname update failed.");
        }
    }

    @PutMapping("/{userId}/password") // 비밀번호 변경
    public ResponseEntity<Object> changePassword(@PathVariable("userId") Long userId,
        @RequestBody UserPasswordChangeRequestDto requestDto) {
        boolean isUpdated = userSettingService.changePassword(userId, requestDto);
        if (isUpdated) {
            return SuccessResponse.createSuccess(SuccessCode.PASSWORD_UPDATE_SUCCESS);
        } else {
            // 비밀번호 변경 실패 시 적절한 에러 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password update failed.");
        }
    }
    }
