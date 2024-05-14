package com.ssafy.piece.global.s3.controller;

import com.ssafy.piece.global.annotation.AuthenticatedUser;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import com.ssafy.piece.global.s3.service.FileService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;


    /**
     * 이미지 업로드
     *
     * @param userId userPk
     * @param fileName  file name
     * @return code, message, data: presigned url
     */
    @GetMapping("/fileupload/{fileName}")
    public ResponseEntity<Object> uploadImage(@AuthenticatedUser Long userId, @PathVariable(name = "fileName") String fileName) {
        ArrayList<String> url = fileService.getPreSignedUrl(userId.toString(), fileName);
        return SuccessResponse.createSuccess(SuccessCode.GET_PRESIGNEDURL_SUCCESS, url);
    }

}