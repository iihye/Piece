package com.ssafy.user.controller;

import com.ssafy.user.service.UserEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEmailController {

    private final UserEmailService userEmailService;


    @GetMapping("/email-validation") //email-check와 비슷해서 email-validation으로 만들었습니다.
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestParam("email") String email) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            String code = userEmailService.sendEmail(email, "check_email");
            resultMap.put("code", code);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(resultMap);
    }
}
