package com.ssafy.piece.domain.cultures.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TmdbResponse {

    private List<MovieResult> results;

}