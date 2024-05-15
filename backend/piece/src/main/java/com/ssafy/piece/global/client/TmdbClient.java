package com.ssafy.piece.global.client;

import com.ssafy.piece.domain.cultures.dto.response.TmdbDetailResponse;
import com.ssafy.piece.domain.cultures.dto.response.TmdbResponse;
import com.ssafy.piece.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdb", url = "https://api.themoviedb.org/3", configuration = FeignClientConfig.class)
public interface TmdbClient {

    @GetMapping("/discover/movie?language=ko-KR&sort_by=popularity.desc")
    TmdbResponse getTMDBMovieList(
        @RequestParam("primary_release_date.lte") String endDate,
        @RequestParam("primary_release_date.gte") String startDate,
        @RequestParam("page") int pageNum,
        @RequestHeader("Authorization") String tmdbApiKey
    );

    @GetMapping("/movie/{movieId}?append_to_response=credits&language=ko-KR")
    TmdbDetailResponse getTmdbMovie(
        @PathVariable("movieId") String movieId,
        @RequestHeader("Authorization") String tmdbApiKey
    );

    @GetMapping("/search/movie?include_adult=false&language=ko-KR")
    TmdbResponse searchTMDBMovie(
        @RequestParam(name = "query") String query,
        @RequestHeader("Authorization") String tmdbApiKey

    );

}
