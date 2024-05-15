package com.ssafy.piece.domain.statistics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewResponseDto {

    private int viewYear;

    private int movieNumber;

    private int TheaterNumber;

    private int musicalNumber;

    private int concertNumber;

    private int etcNumber;

}
