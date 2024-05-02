package com.ssafy.piece.global.s3.controller;

import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import com.ssafy.piece.global.s3.service.FileService;
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
     * @param image
     * @return
     */
    @GetMapping("/upload/{image}")
    public ResponseEntity<Object> uploadImage(@PathVariable(name = "image") String image) {
        String url = fileService.getPreSignedUrl("test", image);
        log.info("url is " + url);
        return SuccessResponse.createSuccess(SuccessCode.GET_PRESIGNEDURL_SUCCESS, url);
    }

    /**
     * 이미지 삭제
     * @param prefix 이미지가 저장된 디렉토리
     * @param image 삭제할 이미지 파일명
     * @return
     */
//    @DeleteMapping("/delete/{prefix}/{image}")
//    public ResponseEntity<String> deleteImage(@PathVariable String prefix, @PathVariable String image) {
//        fileService.deleteImage(prefix, image);
//        return ResponseEntity.ok("Image successfully deleted.");
//    }
}