package com.ssafy.piece.global.client;

import com.ssafy.piece.domain.cultures.dto.response.KobisResponse;
import com.ssafy.piece.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kobis", url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest", configuration = FeignClientConfig.class)
public interface KobisClient {

    @GetMapping("/movie/searchMovieList.json")
    KobisResponse getMovieList(
        @RequestParam("key") String apiKey,
        @RequestParam("curPage") int curPage,
        @RequestParam("itemPerPage") int itemPerPage
    );

}
