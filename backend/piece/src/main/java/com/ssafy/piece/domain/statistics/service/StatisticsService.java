package com.ssafy.piece.domain.statistics.service;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.statistics.dto.response.ConsumptionResponseDto;
import com.ssafy.piece.domain.statistics.dto.response.ViewResponseDto;
import com.ssafy.piece.domain.statistics.entity.Consumptions;
import com.ssafy.piece.domain.statistics.entity.Views;
import com.ssafy.piece.domain.statistics.repository.ConsumptionsRepository;
import com.ssafy.piece.domain.statistics.repository.ViewsRepository;
import com.ssafy.piece.domain.statistics.exception.StatisticsConsumptionNullException;
import com.ssafy.piece.domain.statistics.exception.StatisticsViewNullException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsService {

    private final ViewsRepository viewsRepository;
    private final ConsumptionsRepository consumptionsRepository;

    // 관람 수 계산
    public void modifyViews(Long userId, CultureType type, LocalDate date) {

        Views oldViews = viewsRepository.findByUserIdAndViewYear(userId, date.getYear());

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

        Consumptions consumption = consumptionsRepository.findByuserIdAndConsumptionYearAndConsumptionMonth(
            userId, date.getYear(), date.getMonthValue());

        Consumptions updateConsumption;

        if(consumption!=null) {

            updateConsumption = Consumptions.builder()
                .userId(userId)
                .consumptionYear(date.getYear())
                .consumptionMonth(date.getMonthValue())
                .consumptionMoney(consumption.getConsumptionMoney() + price)
                .build();
        }else{
            updateConsumption = Consumptions.builder()
                .userId(userId)
                .consumptionYear(date.getYear())
                .consumptionMonth(date.getMonthValue())
                .consumptionMoney(price)
                .build();
        }

        consumptionsRepository.save(updateConsumption);
    }

    // 관람 수 통계 조회
    public ViewResponseDto findView(Long userId, int year) {
        Views view = viewsRepository.findByUserIdAndViewYear(userId, year);

            if(view!=null){
        ViewResponseDto result = ViewResponseDto.builder()
            .viewYear(view.getViewYear())
            .movieNumber(view.getMovieNumber())
            .TheaterNumber(view.getTheaterNumber())
            .musicalNumber(view.getMusicalNumber())
            .concertNumber(view.getConcertNumber())
            .etcNumber(view.getEtcNumber())
            .build();
                return result;
            } else{
                throw new StatisticsViewNullException();
            }
    }

    // 소비 금액 통계 조회
    public List<ConsumptionResponseDto> findConsumption(Long userId, int year) {

        Sort sort = Sort.by(Sort.Direction.ASC, "consumptionMonth");

        List<Consumptions> consumptions = consumptionsRepository.findByUserIdAndConsumptionYear(
            userId, year,sort);

        if (consumptions.isEmpty()) {
            throw new StatisticsConsumptionNullException();
        }

        List<ConsumptionResponseDto> result = new ArrayList<>();
        for(Consumptions consumption : consumptions) {
            result.add(ConsumptionResponseDto.builder()
                    .consumptionYear(consumption.getConsumptionYear())
                    .consumptionMonth(consumption.getConsumptionMonth())
                    .consumptionMoney(consumption.getConsumptionMoney())
                .build());
        }
        return result;
    }
}
