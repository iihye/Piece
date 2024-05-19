package com.ssafy.piece.domain.pieces.controller;

import com.ssafy.piece.domain.pieces.dto.response.PieceListResponseDto;
import com.ssafy.piece.domain.pieces.dto.response.PieceRecentResponseDto;
import com.ssafy.piece.domain.pieces.service.PieceListService;
import com.ssafy.piece.global.annotation.AuthenticatedUser;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/piecelist")
public class PieceListController {

    private final PieceListService pieceListService;

    // 조각 리스트
    @GetMapping()
    public ResponseEntity<Object> findPieces(@AuthenticatedUser Long userId) {
        List<PieceListResponseDto> pieceListResponseDtos = pieceListService.listPieces(userId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_PIECE_LIST_SUCCESS,
            pieceListResponseDtos);
    }

    // 내 조각 리스트
    @GetMapping("/my")
    public ResponseEntity<Object> findMyPieces(@AuthenticatedUser Long userId) {
        List<PieceListResponseDto> pieceListResponseDtos = pieceListService.listMyPieces(userId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_MY_PIECE_LIST_SUCCESS,
            pieceListResponseDtos);
    }

    // 찜한 조각 리스트
    @GetMapping("/heart")
    public ResponseEntity<Object> findHeartPieces(@AuthenticatedUser Long userId) {
        List<PieceListResponseDto> pieceListResponseDtos = pieceListService.listHeartPieces(
            userId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_HEART_PIECE_LIST_SUCCESS,
            pieceListResponseDtos);
    }

    // 최근 조각 리스트
    @GetMapping("/recent")
    public ResponseEntity<Object> findRecentPieces() {
        List<PieceRecentResponseDto> pieceRecentResponseDtos = pieceListService.listRecentPieces();

        return SuccessResponse.createSuccess(SuccessCode.FIND_RECENT_PIECE_LIST_SUCCESS,
            pieceRecentResponseDtos);
    }

    // 내 조각 리스트 캘린더
    @GetMapping("/my/{year}/{month}")
    public ResponseEntity<Object> findCalendarMyPieces(@AuthenticatedUser Long userId,
        @PathVariable int year, @PathVariable int month) {
        List<PieceListResponseDto> pieceListResponseDtos = pieceListService.listCalendarMyPieces(
            userId, year, month);

        if (pieceListResponseDtos.isEmpty()) {
            return SuccessResponse.createSuccess(SuccessCode.FIND_MY_PIECE_LIST_NULL_SUCCESS,
                pieceListResponseDtos);
        }
        return SuccessResponse.createSuccess(SuccessCode.FIND_MY_PIECE_LIST_SUCCESS,
            pieceListResponseDtos);
    }


}
