package com.ssafy.piece.domain.pieces.dto.response;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PieceListResponseDto {

    private Long pieceId;
    private CultureType performanceType;
    private String title;
    private LocalDate date;
    private String frontImg;
    private boolean isHeart;
}
