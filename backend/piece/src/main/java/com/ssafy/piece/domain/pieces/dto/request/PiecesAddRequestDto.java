package com.ssafy.piece.domain.pieces.dto.request;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PiecesAddRequestDto {

    private CultureType performanceType;
    private String title;
    private LocalDate date;
    private LocalTime time;
    private String cast;
    private String supervision;
    private String seat;
    private int price;
    private String address;
    private float score;
    private String comment;
    private boolean isOpen;
    private String imageFront;
    private String imageBack;
}
