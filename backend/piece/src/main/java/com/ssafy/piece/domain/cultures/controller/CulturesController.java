package com.ssafy.piece.domain.cultures.controller;

import com.ssafy.piece.domain.cultures.dto.response.CulturesResponse;
import com.ssafy.piece.domain.cultures.dto.response.TmdbDetailResponse;
import com.ssafy.piece.domain.cultures.repository.CulturesQueryDsl;
import com.ssafy.piece.domain.cultures.service.CultureApiService;
import com.ssafy.piece.domain.cultures.service.CulturesService;
import com.ssafy.piece.global.annotation.AuthenticatedUser;
import com.ssafy.piece.global.client.KobisClient;
import com.ssafy.piece.global.client.TmdbClient;
import com.ssafy.piece.global.dto.PageResponse;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("cultures")
public class CulturesController {

    private final TmdbClient tmdbClient;
    private final KobisClient kobisClient;
    private final CultureApiService cultureApiService;
    private final CulturesService culturesService;
    private final CulturesQueryDsl culturesQueryDsl;

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

    @GetMapping("/tmdb/{movieId}")
    public ResponseEntity<Object> getTmdbMovie(@PathVariable("movieId") String movieId) {
        TmdbDetailResponse response = cultureApiService.findMovie(movieId);
        return SuccessResponse.createSuccess(SuccessCode.FIND_TMDB_CAKE_SUCCESS, response);
    }

    @PostMapping("/heart/{cultureId}")
    public ResponseEntity<Object> cultureHeartSave(
        @AuthenticatedUser Long userId,
        @PathVariable("cultureId") Long cultureId) {

        culturesService.addCultureHeart(userId, cultureId);
        return SuccessResponse.createSuccess(SuccessCode.ADD_CULTURE_HEART_SUCCESS);
    }

    @DeleteMapping("/heart/{cultureId}")
    public ResponseEntity<Object> cultureHeartRemove(
        @AuthenticatedUser Long userId,
        @PathVariable("cultureId") Long cultureId) {

        culturesService.removeCultureHeart(userId, cultureId);
        return SuccessResponse.createSuccess(SuccessCode.REMOVE_CULTURE_HEART_SUCCESS);
    }

    @GetMapping("/heart/{cultureId}")
    public ResponseEntity<Object> isCultureHeartExist(
        @AuthenticatedUser Long userId,
        @PathVariable("cultureId") Long cultureId) {

        Boolean result = culturesService.isCultureHeartExist(userId, cultureId);
        return SuccessResponse.createSuccess(SuccessCode.REMOVE_CULTURE_HEART_SUCCESS, result);
    }

    @GetMapping("")
    public ResponseEntity<Object> getCultureList(
        @RequestParam(required = false, name = "genreId") Long genreId,
        @RequestParam(required = false, name = "cultureId") Long cultureId,
        @RequestParam(name = "pageSize") int pageSize
    ) {
        log.info("genreId : {}, cultureId : {}, pageSize : {}", genreId, cultureId, pageSize);
        PageResponse<CulturesResponse> res = culturesQueryDsl.findCultureList(genreId, cultureId,
            pageSize);
        return SuccessResponse.createSuccess(SuccessCode.FIND_CAKE_SUCCESS, res);
    }


}
