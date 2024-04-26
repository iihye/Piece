package com.ssafy.piece.domain.statistics.entity;

import com.ssafy.piece.domain.users.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Consumptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumptionId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users userId;

    @Column(nullable = false)
    private int consumptionYear;

    @Column(nullable = false)
    private int consumptionMonth;

    private int consumptionMoney;

}
