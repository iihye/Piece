package com.ssafy.piece.domain.pieces.controller;

import com.ssafy.piece.domain.pieces.dto.request.PiecesAddRequestDto;
import com.ssafy.piece.domain.pieces.dto.request.RecordImageAddRequestDto;
import com.ssafy.piece.domain.pieces.dto.request.RecordUpdateRequestDto;
import com.ssafy.piece.domain.pieces.dto.response.PieceRecentResponseDto;
import com.ssafy.piece.domain.pieces.dto.response.PiecesDetailResponseDto;
import com.ssafy.piece.domain.pieces.dto.response.RecordDetailResponseDto;
import com.ssafy.piece.domain.pieces.service.PiecesService;
import com.ssafy.piece.global.annotation.AuthenticatedUser;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pieces")
public class PiecesController {

    private final PiecesService piecesService;

    // 조각 등록
    @PostMapping()
    public ResponseEntity<Object> piecesAdd(@AuthenticatedUser Long userId,
        @RequestBody PiecesAddRequestDto piecesAddRequestDto) {
        piecesService.addPieces(userId, piecesAddRequestDto);

        return SuccessResponse.createSuccess(SuccessCode.ADD_PIECE_SUCCESS);
    }

    // 조각 상세
    @GetMapping("/{pieceId}")
    public ResponseEntity<Object> piecesDetail(@AuthenticatedUser Long userId,
        @PathVariable Long pieceId) {
        PiecesDetailResponseDto piecesDetailResponseDto = piecesService.findPieceDetail(userId,
            pieceId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_PIECE_SUCCESS,
            piecesDetailResponseDto);
    }

    // 조각 삭제
    @DeleteMapping("/{pieceId}")
    public ResponseEntity<Object> piecesDelete(@AuthenticatedUser Long userId,
        @PathVariable Long pieceId) {
        piecesService.deletePiece(userId, pieceId);

        return SuccessResponse.createSuccess(SuccessCode.DELETE_PIECE_SUCCESS);
    }

    // 기록 수정
    @PutMapping("/record")
    public ResponseEntity<Object> recordUpdate(@AuthenticatedUser Long userId,
        @RequestBody RecordUpdateRequestDto recordUpdateRequestDto) {
        piecesService.updateRecord(userId, recordUpdateRequestDto);

        return SuccessResponse.createSuccess(SuccessCode.UPDATE_RECORD_SUCCESS);
    }

    // 기록 조회
    @GetMapping("/record/{pieceId}")
    public ResponseEntity<Object> recordDetail(@AuthenticatedUser Long userId,
        @PathVariable Long pieceId) {
        RecordDetailResponseDto recordDetailResponseDto = piecesService.findRecordDetail(userId,
            pieceId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_RECORD_SUCCESS,
            recordDetailResponseDto);
    }

    // 1년 전 조각 조회
    @GetMapping("/year")
    public ResponseEntity<Object> piecesYear(@AuthenticatedUser Long userId) {
        PieceRecentResponseDto pieceRecentResponseDto = piecesService.findPieceYear(userId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_PIECE_RECENT_SUCCESS,
            pieceRecentResponseDto);
    }

    // 기록 이미지 저장
    @PostMapping("/record/image")
    public ResponseEntity<Object> recordImageAdd(@AuthenticatedUser Long userId, @RequestBody RecordImageAddRequestDto recordImageAddRequestDto) {
        String s3path = recordImageAddRequestDto.getS3path();
        log.info("s3path: {}", s3path);
        piecesService.savePieceRecordImage(userId, recordImageAddRequestDto);
        return SuccessResponse.createSuccess(SuccessCode.SAVE_IMAGE_SUCCESS);
    }

    // 기록 이미지 삭제
    @DeleteMapping("/record/image/{piecesimageId}")
    public ResponseEntity<Object> recordImageDelete(@AuthenticatedUser Long userId, @PathVariable Long piecesimageId){
        piecesService.deletePieceRecordImage(userId, piecesimageId);
        return SuccessResponse.createSuccess(SuccessCode.DELETE_IMAGE_SUCCESS);
    }
}
