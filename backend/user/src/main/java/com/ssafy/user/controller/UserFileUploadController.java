package com.ssafy.user.controller;

import com.ssafy.user.global.annotation.AuthenticatedUser;
import com.ssafy.user.global.response.code.SuccessCode;
import com.ssafy.user.global.response.structure.SuccessResponse;
import com.ssafy.user.service.UserFileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserFileUploadController {

    private final UserFileUploadService userFileUploadService;


    @PutMapping("/save/{s3filepath}")
    public ResponseEntity<Object> saveS3Path(@AuthenticatedUser Long userId, @PathVariable(name = "s3filepath") String s3filepath) {
        log.info("s3 filepath : {}", s3filepath);
        userFileUploadService.saveUserProfileImage(userId.toString(), s3filepath);
        return SuccessResponse.createSuccess(SuccessCode.SAVE_IMAGE_SUCCESS);
    }

    @DeleteMapping("/delete/profileimage")
    public ResponseEntity<Object> deleteProfileImage(@AuthenticatedUser Long userId) {
        userFileUploadService.deleteProfileImage(userId);
        return SuccessResponse.createSuccess(SuccessCode.DELETE_IMAGE_SUCCESS);
    }

}
