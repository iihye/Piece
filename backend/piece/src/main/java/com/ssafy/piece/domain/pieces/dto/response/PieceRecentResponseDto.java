package com.ssafy.piece.domain.pieces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PieceRecentResponseDto {

    private Long pieceId;
    private String frontImg;
    private String backImg;
}
