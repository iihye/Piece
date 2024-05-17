package com.ssafy.piece.domain.pieces.controller;

import com.ssafy.piece.domain.pieces.dto.response.PieceListResponseDto;
import com.ssafy.piece.domain.pieces.service.PieceSearchService;
import com.ssafy.piece.global.annotation.AuthenticatedUser;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class PieceSerchController {

    private final PieceSearchService pieceSearchService;

    // 조각 검색하기
    @GetMapping("/{keyword}")
    public ResponseEntity<Object> pieceSearch(@AuthenticatedUser Long userId,@PathVariable String keyword){
        List<PieceListResponseDto> result = pieceSearchService.searchPiece(keyword, userId);
        return  SuccessResponse.createSuccess(SuccessCode.SEARCH_PIECE_SUCCESS,result);
    }

    // 내 조각 검색하기
    @GetMapping("/my/{keyword}")
    public ResponseEntity<Object> pieceMySearch(@AuthenticatedUser Long userId,@PathVariable String keyword){
        List<PieceListResponseDto> result = pieceSearchService.searchMyPiece(keyword, userId);
        return  SuccessResponse.createSuccess(SuccessCode.SEARCH_MY_PIECE_SUCCESS, result);
    }

}
