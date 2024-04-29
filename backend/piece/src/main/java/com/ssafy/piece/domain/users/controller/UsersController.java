package com.ssafy.piece.domain.users.controller;

import com.ssafy.piece.global.annotation.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class UsersController {


    @GetMapping("/hi")
    public String hi(@AuthenticatedUser Long userId) {
        log.info("userID : {}", userId);
        return "hi";
    }
}