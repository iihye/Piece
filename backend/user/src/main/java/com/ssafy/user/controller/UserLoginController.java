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








//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class UserLoginController {
//
//    private final UserLoginService userLoginService;
//    private final JwtTokenUtil jwtTokenUtil;
//    private static final Logger log = LoggerFactory.getLogger(UserLoginController.class);
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto loginRequest) {
//        log.info("로그인 시작 : 사용자 이메일은 {} " , loginRequest.getEmail());
//        try {
//            Long userId = userLoginService.login(loginRequest);
//            log.info("로그인 서비스 호출 완료 : userId는 {}" , userId);
//            if (userId != null) {
//                String token = jwtTokenUtil.generateToken(userId);
//                log.info("토큰 생성 성공: {}", token); // 토큰 생성 로그
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.add("Authorization", "Bearer " + token); // 토큰을 헤더에 추가
//                log.info("응답 헤더에 Authorization 추가됨"); // 헤더 추가 로그
//
//                Map<String, Object> response = new HashMap<>();
//                response.put("token", token);
//                log.info("로그인 성공 응답 반환 준비"); // 응답 반환 준비 로그
//                return ResponseEntity.ok().headers(headers).body(SuccessResponse.createSuccess(SuccessCode.LOGIN_SUCCESS, response));
//            } else {
//                log.warn("로그인 실패: 사용자 ID가 null입니다. 사용자 이메일 {}", loginRequest.getEmail()); // 로그인 실패 로그
//                return ResponseEntity.status(ErrorCode.UNAUTHORIZED_REQUEST.getHttpStatus())
//                    .body(ErrorCode.UNAUTHORIZED_REQUEST.getMessage());
//            }
//        } catch (Exception e) {
//            log.error("로그인 중 예외 발생: 사용자 이메일 {}", loginRequest.getEmail(), e); // 예외 발생 로그
//            return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getHttpStatus())
//                .body(ErrorCode.USER_NOT_FOUND.getMessage());
//        }
//    }
//}
