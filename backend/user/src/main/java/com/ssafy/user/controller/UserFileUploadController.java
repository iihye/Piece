package com.ssafy.user.controller;

import com.ssafy.user.global.annotation.AuthenticatedUser;
import com.ssafy.user.global.response.code.SuccessCode;
import com.ssafy.user.global.response.structure.SuccessResponse;
import com.ssafy.user.service.UserFileUploadService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserFileUploadController {

    private final UserFileUploadService userFileUploadService;


    /**
     * db 테이블에 imageURL 저장
     *
     * @param userId
     * @param body
     * @return
     */
    @PutMapping("/save/profileimage")
    public ResponseEntity<Object> saveS3Path(@AuthenticatedUser Long userId, @RequestBody Map<String, String> body) {
        String s3path = body.get("s3path");
        log.info("s3path: {}", s3path);
        userFileUploadService.saveUserProfileImage(userId.toString(), s3path);
        return SuccessResponse.createSuccess(SuccessCode.SAVE_IMAGE_SUCCESS);
    }


    /**
     * db 테이블에 저장되어 있는 imageURL 삭제
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/profileimage")
    public ResponseEntity<Object> deleteProfileImage(@AuthenticatedUser Long userId) {
        userFileUploadService.deleteProfileImage(userId);
        return SuccessResponse.createSuccess(SuccessCode.DELETE_IMAGE_SUCCESS);
    }

}
