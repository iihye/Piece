package com.ssafy.piece.domain.cultures.controller;

import com.ssafy.piece.domain.cultures.dto.response.CultureDetailResponse;
import com.ssafy.piece.domain.cultures.dto.response.CulturesResponse;
import com.ssafy.piece.domain.cultures.entity.CultureType;
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
        CultureDetailResponse response = cultureApiService.findMovie(movieId);
        return SuccessResponse.createSuccess(SuccessCode.FIND_TMDB_CULTURE_SUCCESS, response);
    }

    @GetMapping("/kopis/{concertId}")
    public ResponseEntity<Object> getKopisMovie(
        @PathVariable(name = "concertId") String concertId
    ) {
        CultureDetailResponse response = cultureApiService.findConcert(concertId);
        return SuccessResponse.createSuccess(SuccessCode.FIND_KOPIS_CULTURE_SUCCESS, response);
    }

    @PostMapping("/heart/{cultureId}")
    public ResponseEntity<Object> cultureHeartSave(
        @AuthenticatedUser Long userId,
        @PathVariable("cultureId") Long cultureId) {

        culturesService.addCultureHeart(userId, cultureId);
        log.info("culture id is {} ", cultureId);
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

    @GetMapping("/heart/count/{cultureId}")
    public ResponseEntity<Object> getCultureHeartCount(
        @PathVariable(name = "cultureId") Long cultureId) {
        log.info("getCultureId : {}", cultureId);

        return SuccessResponse.createSuccess(SuccessCode.COUNT_CULTURE_HEART_SUCCESS,
            culturesService.countCultureList(cultureId));
    }

    @GetMapping("")
    public ResponseEntity<Object> getCultureList(
        @RequestParam(required = false, name = "cultureType") CultureType cultureType,
        @RequestParam(required = false, name = "startPageId") Long startPageId,
        @RequestParam(required=false,name="title") String title,
        @RequestParam(name = "pageSize") int pageSize
    ) {
        log.info("startPageId : {}, pageSize : {}", startPageId, pageSize);
        PageResponse<CulturesResponse> res = culturesQueryDsl.findCultureList(cultureType,
            startPageId,title,
            pageSize);
        return SuccessResponse.createSuccess(SuccessCode.FIND_CULTURE_SUCCESS, res);
    }

    @GetMapping("/{cultureId}")
    public ResponseEntity<Object> getCulture(@PathVariable(name = "cultureId") Long cultureId) {
        log.info("getCultureId : {}", cultureId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_CULTURE_SUCCESS,
            culturesService.findCulture(cultureId));
    }

    @GetMapping("/tmdb/search")
    public ResponseEntity<Object> searchTMDBMovie(
        @RequestParam(name = "movieName") String movieName
    ) {
        log.info("searchTMDBMovie : {}", movieName);
        return SuccessResponse.createSuccess(SuccessCode.FIND_TMDB_CULTURE_SUCCESS,
            cultureApiService.searchMovie(movieName));
    }

    @GetMapping("/image")
    public ResponseEntity<Object> fetchImage(
        @RequestParam(name = "imageUrl") String imageUrl
    ) {
        return SuccessResponse.createSuccess(SuccessCode.FETCH_IMAGE_SUCCESS,
            culturesService.fetchImage(imageUrl));
    }

}
