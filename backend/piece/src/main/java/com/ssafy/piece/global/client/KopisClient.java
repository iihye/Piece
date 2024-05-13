package com.ssafy.piece.global.client;

import com.ssafy.piece.domain.cultures.dto.xml.KopisResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kopis", url = "http://www.kopis.or.kr/openApi/restful/pblprfr")
public interface KopisClient {

    @GetMapping("/{concertId}?newsql=Y")
    KopisResponse getKopisData(
        @PathVariable(name = "concertId") String concertId,
        @RequestParam(name = "service") String service
    );
}
