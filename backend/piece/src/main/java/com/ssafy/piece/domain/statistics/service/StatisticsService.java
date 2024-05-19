package com.ssafy.piece.domain.statistics.service;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.statistics.dto.response.ConsumptionResponseDto;
import com.ssafy.piece.domain.statistics.dto.response.ViewResponseDto;
import com.ssafy.piece.domain.statistics.entity.Consumptions;
import com.ssafy.piece.domain.statistics.entity.Views;
import com.ssafy.piece.domain.statistics.repository.ConsumptionsRepository;
import com.ssafy.piece.domain.statistics.repository.ViewsRepository;
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

        Views views = viewsRepository.findByUserIdAndViewYear(userId, date.getYear());

        if (views == null) {
            views = Views.builder().build();
        }

        Views updateViews = views;

        switch (type) {
            case MOVIE:
                updateViews = views.toBuilder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .movieNumber(views.getMovieNumber() + 1)
                    .build();
                break;
            case THEATER:
                updateViews = views.toBuilder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .TheaterNumber(views.getTheaterNumber() + 1)
                    .build();
                break;
            case MUSICAL:
                updateViews = views.toBuilder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .musicalNumber(views.getMusicalNumber() + 1)
                    .build();
                break;
            case CONCERT:
                updateViews = views.toBuilder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .concertNumber(views.getConcertNumber() + 1)
                    .build();
                break;
            case ETC:
                updateViews = views.toBuilder()
                    .userId(userId)
                    .viewYear(date.getYear())
                    .etcNumber(views.getEtcNumber() + 1)
                    .build();
                break;
        }
            viewsRepository.save(updateViews);



    }

    // 소비 금액 계산
    public void modifyConsumption(Long userId, LocalDate date, int price) {

        Consumptions consumption = consumptionsRepository.findByuserIdAndConsumptionYearAndConsumptionMonth(
            userId, date.getYear(), date.getMonthValue());

        if(consumption==null){
            consumption = Consumptions.builder().build();
        }

            Consumptions updateConsumption = consumption.toBuilder()
                .userId(userId)
                .consumptionYear(date.getYear())
                .consumptionMonth(date.getMonthValue())
                .consumptionMoney(consumption.getConsumptionMoney()+price)
                .build();

        consumptionsRepository.save(updateConsumption);
        }

    // 관람 수 통계 조회
    public ViewResponseDto findView(Long userId, int year) {
        Views view = viewsRepository.findByUserIdAndViewYear(userId, year);

        if(view==null){
            view = Views.builder()
                .userId(userId)
                .viewYear(year)
                .build();
        }

        ViewResponseDto result = ViewResponseDto.builder()
            .viewYear(view.getViewYear())
            .movieNumber(view.getMovieNumber())
            .theaterNumber(view.getTheaterNumber())
            .musicalNumber(view.getMusicalNumber())
            .concertNumber(view.getConcertNumber())
            .etcNumber(view.getEtcNumber())
            .build();

        return result;
    }

    // 소비 금액 통계 조회
    public List<ConsumptionResponseDto> findConsumption(Long userId, int year) {

        Sort sort = Sort.by(Sort.Direction.ASC, "consumptionMonth");

        List<Consumptions> consumptions = consumptionsRepository.findByUserIdAndConsumptionYear(
            userId, year,sort);

        if(consumptions==null){
            consumptions = new ArrayList<>();
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
