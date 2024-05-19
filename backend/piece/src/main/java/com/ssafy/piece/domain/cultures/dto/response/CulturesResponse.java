package com.ssafy.piece.domain.cultures.dto.response;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CulturesResponse {

    private Long CultureId;

    private CultureType cultureType;

    private String code;

    private String title;

    private String imageUrl;

    private List<Long> genreIdList;

}
