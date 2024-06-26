package com.ssafy.piece.domain.statistics.repository;

import com.ssafy.piece.domain.statistics.entity.Consumptions;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionsRepository extends JpaRepository<Consumptions, Long> {

    Consumptions findByuserIdAndConsumptionYearAndConsumptionMonth(Long userId, int year,
        int month);

    List<Consumptions> findByUserIdAndConsumptionYear(Long userId, int year, Sort sort);

}
