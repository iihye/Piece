package com.ssafy.piece.domain.pieces.controller;

import com.ssafy.piece.domain.pieces.dto.request.ImageRequestDto;
import com.ssafy.piece.domain.pieces.service.ImageMakeService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageMakeController {

    private final ImageMakeService imageMakeService;


    // 사용자가 올린 티켓 앞면 이미지를 이용해 뒷면 이미지 생성
    @PostMapping("/make")
    public ResponseEntity<Object> imageMake(@RequestBody ImageRequestDto image){

        String backImage = imageMakeService.MakePrompt(image.getFile());

        return SuccessResponse.createSuccess(SuccessCode.CREATE_IMAGE_SUCCESS,backImage);
    }
}
