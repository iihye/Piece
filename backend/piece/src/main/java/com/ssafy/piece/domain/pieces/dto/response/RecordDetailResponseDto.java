package com.ssafy.piece.domain.pieces.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDetailResponseDto {

    private Long recordId;
    private Long pieceId;
    private String record;
    private List<String> imgList;
}
