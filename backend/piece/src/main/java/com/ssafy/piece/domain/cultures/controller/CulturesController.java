package com.ssafy.piece.domain.cultures.controller;

import com.ssafy.piece.domain.cultures.service.CultureApiService;
import com.ssafy.piece.global.client.KobisClient;
import com.ssafy.piece.global.client.TmdbClient;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CulturesController {

    private final TmdbClient tmdbClient;
    private final KobisClient kobisClient;
    private final CultureApiService cultureApiService;

    @Value("${secret.kobis.api-key}")
    private String KOBIS_API_KEY;

    @Value("${secret.tmdb.api-key}")
    private String TMDB_API_KEY;

    @GetMapping("/kobis")
    public ResponseEntity<Object> saveKobisMovie() {
        return SuccessResponse.createSuccess(SuccessCode.JOIN_SUCCESS,
            kobisClient.getMovieList(KOBIS_API_KEY, 1, 20));
    }

//    @GetMapping("/tmdb")
//    public ResponseEntity<Object> saveTmdbMovie() {
//        cultureApiService.collectMovieData();
//        return SuccessResponse.createSuccess(SuccessCode.JOIN_SUCCESS);
//    }
}
