package com.ssafy.piece.domain.statistics.controller;

import com.ssafy.piece.domain.statistics.entity.Consumptions;
import com.ssafy.piece.domain.statistics.entity.Views;
import com.ssafy.piece.domain.statistics.service.StatisticsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    // 관람 통계 조회
    @GetMapping("/view")
    public ResponseEntity<Object> viewDetail(@PathVariable int viewYear) {

        Long userId = 1L;
        Views view = statisticsService.findView(userId, viewYear);

        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    // 소비 통계 조회
    @GetMapping("/consumption")
    public ResponseEntity<Object> consumptionDetail(@PathVariable int consumptionYear) {

        Long userId = 1L;
        List<Consumptions> consumption = statisticsService.findConsumption(userId, consumptionYear);

        return new ResponseEntity<>(consumption, HttpStatus.OK);
    }

}
