package com.ssafy.piece.domain.reports.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "reports")
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportsId;

    @Column(nullable=false)
    private Long reporterUserId;

    @Column(nullable=false)
    private Long pieceId;

    @Column(nullable=false)
    private int reportType;
}
