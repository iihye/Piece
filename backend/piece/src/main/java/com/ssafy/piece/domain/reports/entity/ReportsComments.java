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
@Table(name = "reports_comments")
public class ReportsComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportsCommentsId;

    @Column(nullable=false)
    private Long userId;

    @Column(nullable=false)
    private String comments;
}
