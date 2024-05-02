package com.ssafy.piece.domain.users.controller;

import com.ssafy.piece.domain.users.dto.request.UserLoginRequestDto;
import com.ssafy.piece.domain.users.service.UserLoginService;
import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;
    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);


    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto loginRequest) {
        try {
            boolean isAuthenticated = userLoginService.login(loginRequest);
            if (isAuthenticated) {
                log.info("Login successful for user: {}", loginRequest.getEmail());
                return SuccessResponse.createSuccess(SuccessCode.LOGIN_SUCCESS);
            } else {
                log.warn("Unauthorized login attempt for user: {}", loginRequest.getEmail());
                return ResponseEntity
                    .status(ErrorCode.UNAUTHORIZED_REQUEST.getHttpStatus())
                    .body(ErrorCode.UNAUTHORIZED_REQUEST.getMessage());
            }
        } catch (Exception e) {
            log.error("Login error for user: {}", loginRequest.getEmail(), e);
            return ResponseEntity
                .status(ErrorCode.USER_NOT_FOUND.getHttpStatus())
                .body(ErrorCode.USER_NOT_FOUND.getMessage());
        }
    }


}
