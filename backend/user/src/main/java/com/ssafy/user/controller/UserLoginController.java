package com.ssafy.user.controller;

import com.ssafy.user.dto.request.UserLoginRequestDto;
import com.ssafy.user.global.response.code.ErrorCode;
import com.ssafy.user.global.response.code.SuccessCode;
import com.ssafy.user.global.response.structure.SuccessResponse;
import com.ssafy.user.service.UserLoginService;
import com.ssafy.user.global.security.JwtTokenUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;
    private final JwtTokenUtil jwtTokenUtil; // JwtTokenUtil 주입
    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto loginRequest) {
        try {
            Long userId = userLoginService.login(loginRequest);
            if (userId != null) {
                String token = jwtTokenUtil.generateToken(userId); // JWT 생성
                log.info("Login successful for user ID: {}", userId);
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("userId", userId);
                return  SuccessResponse.createSuccess(SuccessCode.LOGIN_SUCCESS,response);
            } else {
                log.warn("Unauthorized login attempt for user: {}", loginRequest.getEmail());
                return ResponseEntity.status(ErrorCode.UNAUTHORIZED_REQUEST.getHttpStatus())
                        .body(ErrorCode.UNAUTHORIZED_REQUEST.getMessage());
            }
        } catch (Exception e) {
            log.error("Login error for user: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getHttpStatus())
                    .body(ErrorCode.USER_NOT_FOUND.getMessage());
        }
    }


}