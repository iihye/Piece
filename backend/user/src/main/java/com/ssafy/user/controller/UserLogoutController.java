package com.ssafy.user.controller;

import com.ssafy.user.global.response.code.SuccessCode;
import com.ssafy.user.global.response.structure.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserLogoutController {
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // 클라이언트 측에서 JWT를 삭제하도록 안내
        log.info("User requested logout. Ensure JWT is discarded on client side.");

        // 로그아웃 성공 응답 반환
        return SuccessResponse.createSuccess(SuccessCode.LOGOUT_SUCCESS);
    }
}