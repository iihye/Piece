package com.ssafy.piece.domain.pieces.controller;

import com.ssafy.piece.domain.pieces.dto.request.PiecesAddRequestDto;
import com.ssafy.piece.domain.pieces.dto.response.PiecesDetailResponseDto;
import com.ssafy.piece.domain.pieces.entity.Pieces;
import com.ssafy.piece.domain.pieces.service.PiecesService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pieces")
public class PiecesController {

    private final PiecesService piecesService;

    // 조각 등록
    @PostMapping()
    public ResponseEntity<Object> piecesAdd(@RequestBody PiecesAddRequestDto piecesAddRequestDto) {
        Pieces pieces = piecesService.addPieces(piecesAddRequestDto);

        return SuccessResponse.createSuccess(SuccessCode.ADD_PIECE_SUCCESS, pieces);
    }

    // 조각 상세
    @GetMapping("/{pieceId}")
    public ResponseEntity<Object> piecesDetail(@PathVariable Long pieceId) {
        PiecesDetailResponseDto piecesDetailResponseDto = piecesService.findPieceDetail(pieceId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_PIECE_SUCCESS,
            piecesDetailResponseDto);
    }

    // 조각 삭제
    @DeleteMapping("/{pieceId}")
    public ResponseEntity<Object> piecesDelete(@PathVariable Long pieceId) {
        piecesService.deletePiece(pieceId);

        return SuccessResponse.createSuccess(SuccessCode.DELETE_PIECE_SUCCESS);
    }
}
