package com.ssafy.piece.domain.statistics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionResponseDto {

    private int consumptionYear;

    private int consumptionMonth;

    private int consumptionMoney;

}
