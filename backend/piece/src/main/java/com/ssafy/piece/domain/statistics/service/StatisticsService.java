package com.ssafy.piece.domain.statistics.service;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.statistics.entity.Consumptions;
import com.ssafy.piece.domain.statistics.entity.Views;
import com.ssafy.piece.domain.statistics.repository.ConsumptionsRepository;
import com.ssafy.piece.domain.statistics.repository.ViewsRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ViewsRepository viewsRepository;
    private final ConsumptionsRepository consumptionsRepository;

    // 관람 수 계산
    public void modifyViews(Long userId, CultureType type, LocalDate date) {

        Views oldViews = viewsRepository.findByuserIdAndviewYear(userId, date.getYear());
        Views views = Views.builder().build();

        if (oldViews != null) {
            oldViews = views;
        }

        switch (type) {
            case MOVIE:
                views = Views.builder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .movieNumber(oldViews.getMovieNumber() + 1)
                    .build();
            case THEATER:
                views = Views.builder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .movieNumber(oldViews.getTheaterNumber() + 1)
                    .build();
            case MUSICAL:
                views = Views.builder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .movieNumber(oldViews.getMusicalNumber() + 1)
                    .build();
            case CONCERT:
                views = Views.builder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .movieNumber(oldViews.getConcertNumber() + 1)
                    .build();
            case ETC:
                views = Views.builder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .movieNumber(oldViews.getEtcNumber() + 1)
                    .build();
        }

        viewsRepository.save(views);

    }

    // 소비 금액 계산
    public void modifyConsumption(Long userId, LocalDate date, int price) {

        Consumptions consumption = consumptionsRepository.findByuserIdAndConsumptionsYearAndConsumptionMonth(
            userId, date.getYear(), date.getMonthValue());

        consumption = Consumptions.builder()
            .userId(userId)
            .consumptionYear(date.getYear())
            .consumptionMonth(date.getMonthValue())
            .consumptionMoney(price)
            .build();

        consumptionsRepository.save(consumption);
    }

    // 관람 수 통계 조회
    public Views findView(Long userId, int year) {
        Views view = viewsRepository.findByuserIdAndviewYear(userId, year);
        return view;
    }

    // 소비 금액 통계 조회
    public List<Consumptions> findConsumption(Long userId, int year) {
        List<Consumptions> consumption = consumptionsRepository.findByUserIdAndConsumptionYear(
            userId, year);
        return consumption;
    }
}
