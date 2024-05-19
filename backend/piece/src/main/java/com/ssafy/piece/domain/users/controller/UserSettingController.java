package com.ssafy.piece.domain.users.controller;

import com.ssafy.piece.domain.users.dto.request.UserNicknameChangeRequestDto;
import com.ssafy.piece.domain.users.dto.request.UserPasswordChangeRequestDto;
import com.ssafy.piece.domain.users.service.UserSettingService;
import com.ssafy.piece.global.response.code.ResponseCode;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        ResponseCode responseCode = userSettingService.changeNickname(userId, requestDto);
        return ResponseEntity.status(responseCode.getHttpStatus()).body(responseCode.getMessage());
    }

    @PutMapping("/{userId}/password") // 비밀번호 변경
    public ResponseEntity<Object> changePassword(@PathVariable("userId") Long userId,
        @RequestBody UserPasswordChangeRequestDto requestDto) {
        ResponseCode responseCode = userSettingService.changePassword(userId, requestDto);
        return ResponseEntity.status(responseCode.getHttpStatus()).body(responseCode.getMessage());
    }



    }
