package com.ssafy.piece.domain.cultures.dto.response;

import com.ssafy.piece.domain.cultures.dto.response.TmdbDetailResponse.CastDTO;
import com.ssafy.piece.domain.cultures.dto.xml.Db;
import com.ssafy.piece.domain.cultures.dto.xml.KopisResponse;
import com.ssafy.piece.domain.cultures.entity.CultureGenre;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.domain.cultures.entity.Genres;
import java.util.Arrays;
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


    public static CultureDetailResponse kopisResponseToCultureDetailResponse(
        KopisResponse response, Long cultureId) {
        Db db = response.getDb();
        List<String> castList = Arrays.stream(db.getPrfcast().split(","))
            .map(String::trim)
            .map(cast -> {
                if (cast.endsWith(" 등")) {
                    return cast.substring(0, cast.length() - 2);
                } else {
                    return cast;
                }
            })
            .toList();
//        if (castList.size() > 5) {
//            castList.subList(5, castList.size()).clear();
//        }
        return CultureDetailResponse.builder()
            .cultureId(cultureId)
            .code(db.getMt20id())
            .title(db.getPrfnm())
            .releaseDate(db.getPrfpdfrom())
            .runtime(db.getPrfruntime())
            .overview(db.getSty().replaceAll("[\\r\\n+]", " "))
            .posterImageUrl(db.getPoster())
            .castList(castList)
            .build();
    }

    public static CultureDetailResponse tmdbResponseToCultureDetailResponse(
        TmdbDetailResponse response, Long cultureId) {
        List<String> castList = response.getCredits().getCast().stream()
            .map(CastDTO::getName)
            .toList();
//        if (castList.size() > 5) {
//            castList.subList(5, castList.size()).clear();
//        }
        return CultureDetailResponse.builder()
            .cultureId(cultureId)
            .code(response.getId().toString())
            .title(response.getTitle())
            .releaseDate(response.getRelease_date())
            .runtime(response.getRuntime().toString())
            .overview(response.getOverview())
            .posterImageUrl("https://image.tmdb.org/t/p/w780" + response.getPoster_path())
            .castList(castList)
            .build();
    }
}
