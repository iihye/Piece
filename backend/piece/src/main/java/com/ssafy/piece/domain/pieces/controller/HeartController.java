package com.ssafy.piece.domain.pieces.controller;

import com.ssafy.piece.domain.pieces.service.HeartService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    // 찜 등록
    @PostMapping("/{pieceId}")
    public ResponseEntity<Object> heartAdd(@PathVariable Long pieceId) {
        heartService.addHeart(pieceId);

        return SuccessResponse.createSuccess(SuccessCode.ADD_HEART_SUCCESS);
    }

    // 찜 삭제
    @DeleteMapping("/{pieceId}")
    public ResponseEntity<Object> heartDelete(@PathVariable Long pieceId) {
        heartService.deleteHeart(pieceId);

        return SuccessResponse.createSuccess(SuccessCode.DELETE_HEART_SUCCESS);
    }
}
