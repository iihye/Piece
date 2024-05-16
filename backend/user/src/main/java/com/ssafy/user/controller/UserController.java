package com.ssafy.user.controller;

import com.ssafy.user.entity.Users;
import com.ssafy.user.global.response.code.ErrorCode;
import com.ssafy.user.global.response.code.SuccessCode;
import com.ssafy.user.global.response.structure.SuccessResponse;
import com.ssafy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/find")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}") // UserId를 통해 회원정보를 조회합니다.
    public ResponseEntity<Users> getUserById(@PathVariable Long userId) {
        Users user = userService.getUserById(userId);
        if (user != null) {
            // 패스워드 필드를 null로 설정하여 비밀번호는 안보이게 합니다.
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
