package com.ssafy.piece.domain.pieces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PiecesDetailResponseDto {

    private Long pieceId;
    private String title;
    private String frontImg;
    private String backImg;
}
