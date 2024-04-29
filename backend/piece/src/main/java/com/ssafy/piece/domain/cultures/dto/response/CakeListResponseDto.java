package com.ssafy.piece.domain.cultures.dto.response;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import lombok.Builder;

@Builder
public class CakeListResponseDto {

    private Long cultureId;

    private CultureType cultureType;

    private String title;

    private String imageUrl;

    private String state;


}
