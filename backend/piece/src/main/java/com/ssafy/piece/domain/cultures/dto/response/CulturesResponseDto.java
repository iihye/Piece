package com.ssafy.piece.domain.cultures.dto.response;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CulturesResponseDto {

    private Long CultureId;

    private CultureType cultureType;

    private String code;

    private String title;

    private String imageUrl;

    private String genre;

    private String state;

    private String age;

    private String director;

    private String runtime;
}
