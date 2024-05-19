package com.ssafy.piece.domain.pieces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordImageAddRequestDto {
    private Long pieceId;
    private String s3path;
}
