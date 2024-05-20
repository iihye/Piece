package com.ssafy.chat.global.client;

import com.ssafy.chat.dto.response.CultureFeignResponseDto;
import com.ssafy.chat.dto.response.CultureResponseDto;
import com.ssafy.chat.dto.response.LabelFeignResponseDto;
import com.ssafy.chat.dto.response.LabelResponseDto;
import com.ssafy.chat.global.config.FeignClientConfig;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "piece", url = "${external.piece.host}", configuration = FeignClientConfig.class)
public interface PieceClient {
    @GetMapping("/cultures/{cultureId}")
    CultureFeignResponseDto getCulture(@PathVariable Long cultureId);

    @GetMapping("/labels/{labelId}")
    LabelFeignResponseDto getLabel(@PathVariable Long labelId);
}
