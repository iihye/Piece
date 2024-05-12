package com.ssafy.piece.domain.cultures.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleMovieResponse {

    private String poster_path;
    private String title;

}
