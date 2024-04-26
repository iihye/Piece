package com.ssafy.piece.domain.statistics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Consumptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumptionId;

    private Long userId;

    @Column(nullable = false)
    private int consumptionYear;

    @Column(nullable = false)
    private int consumptionMonth;

    private int consumptionMoney;

}
