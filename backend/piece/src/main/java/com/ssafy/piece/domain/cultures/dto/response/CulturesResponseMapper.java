package com.ssafy.piece.domain.cultures.dto.response;

import com.ssafy.piece.domain.cultures.entity.CultureGenre;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.domain.cultures.entity.Genres;
import java.util.List;
import java.util.stream.Collectors;

public class CulturesResponseMapper {

    public static CulturesResponse cultureEntityToDto(Cultures cultures) {
        return CulturesResponse.builder()
            .CultureId(cultures.getId())
            .code(cultures.getCode())
            .imageUrl(cultures.getImageUrl())
            .title(cultures.getTitle())
            .cultureType(cultures.getCultureType())
            .genreIdList(cultures.getCultureGenre()
                .stream().map(CultureGenre::getGenre).map(Genres::getId)
                .collect(Collectors.toList()))
            .build();
    }

    public static List<CulturesResponse> cultureEntityToDtoList(List<Cultures> cultures) {
        return cultures.stream()
            .map(CulturesResponseMapper::cultureEntityToDto)
            .collect(Collectors.toList());
    }

    public static SimpleMovieResponse movieResultToSimpleMovieResponse(MovieResult movie) {
        return SimpleMovieResponse.builder()
            .poster_path("https://image.tmdb.org/t/p/w400" + movie.getPoster_path())
            .title(movie.getTitle())
            .build();
    }

    public static List<SimpleMovieResponse> movieResultToSimpleMovieResponseList(
        List<MovieResult> list) {
        return list.stream()
            .map(CulturesResponseMapper::movieResultToSimpleMovieResponse)
            .collect(Collectors.toList());
    }
}
