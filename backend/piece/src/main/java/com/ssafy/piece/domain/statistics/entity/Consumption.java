package com.ssafy.piece.domain.statistics.entity;

import com.ssafy.piece.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Consumption {

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
