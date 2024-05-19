package com.ssafy.piece.domain.statistics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "consumptions",

    uniqueConstraints = {
        @UniqueConstraint(
            name = "UniqueUserIdAndconsumptionYear",
            columnNames = {
                "user_id",
                "consumption_year",
                "consumption_month"
            }
        ),
    }
)
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
