package com.ssafy.piece.domain.labels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "labels")
public class Labels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labelId;

//    @Column(nullable = false)
//    private PerformanceType performanceType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;
}
