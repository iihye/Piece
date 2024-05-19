package com.ssafy.piece.domain.cultures.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CultureDetailResponse {

    private Long cultureId;
    private String code;
    private String title;
    private String overview;
    private String posterImageUrl;
    private String releaseDate;
    private String runtime;
    private List<String> castList;

}
