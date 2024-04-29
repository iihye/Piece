package com.ssafy.piece.domain.pieces.entity;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "pieces")
@EntityListeners(AuditingEntityListener.class)
public class Pieces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pieceId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private CultureType performanceType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = true)
    private LocalTime time;

    @Column(nullable = true)
    private String cast;

    @Column(nullable = true)
    private String supervision;

    @Column(nullable = true)
    private String seat;

    @Column(nullable = true)
    private int price;

    @Column(nullable = true)
    private String address;

    @Column(nullable = false)
    private float score;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OpenType openYn;

    @Column(nullable = false)
    private String imageFront;

    @Column(nullable = false)
    private String imageBack;

    @Column(nullable = true)
    private String record;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private GenreType genre;

    public void setRecord(String record) {
        this.record = record;
    }
}
