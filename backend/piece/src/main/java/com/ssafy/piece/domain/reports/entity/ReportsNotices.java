package com.ssafy.piece.domain.reports.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "reports_notices")
@EntityListeners(AuditingEntityListener.class)
public class ReportsNotices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportNoticesId;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
