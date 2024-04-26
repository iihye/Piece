package com.ssafy.chat.global.client;

import com.ssafy.chat.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "piece", url = "${external.piece.host}", configuration = FeignClientConfig.class)
public interface PieceClient {


}
