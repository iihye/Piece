package com.ssafy.piece.domain.pieces.entity;

import com.ssafy.piece.domain.users.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "pieces")

public class Pieces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pieceId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

//    @Column(nullable = false)
//    private PerformanceType performanceType;

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

    @Column(nullable = true)
    private Boolean isOpen;

    @Column(nullable = false)
    private String imageFront;

    @Column(nullable = false)
    private String imageBack;
}
