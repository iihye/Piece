package com.ssafy.piece.domain.statistics.controller;

import com.ssafy.piece.domain.statistics.dto.response.ConsumptionResponseDto;
import com.ssafy.piece.domain.statistics.dto.response.ViewResponseDto;
import com.ssafy.piece.domain.statistics.service.StatisticsService;
import com.ssafy.piece.global.annotation.AuthenticatedUser;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    // 관람 통계 조회
    @GetMapping("/view/{viewYear}")
    public ResponseEntity<Object> viewDetail(@PathVariable int viewYear, @AuthenticatedUser Long userId) {
        ViewResponseDto view = statisticsService.findView(userId, viewYear);
        return SuccessResponse.createSuccess(SuccessCode.FIND_VIEW_STATISTICS_SUCCESS, view);
    }

    // 소비 통계 조회
    @GetMapping("/consumption/{consumptionYear}")
    public ResponseEntity<Object> consumptionDetail(@PathVariable int consumptionYear, @AuthenticatedUser Long userId) {
        List<ConsumptionResponseDto> consumption = statisticsService.findConsumption(userId, consumptionYear);
        return SuccessResponse.createSuccess(SuccessCode.FIND_CONSUMPTIONS_SUCCESS, consumption);
    }

}
