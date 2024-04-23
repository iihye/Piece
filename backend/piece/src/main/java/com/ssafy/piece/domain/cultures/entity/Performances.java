package com.ssafy.piece.domain.cultures.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Table(name = "performances")
@Getter
public class Performances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culture_id")
    private Cultures cultures;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "spot_name")
    private String spotName;

    private String area;

    private String production;

    private String agency;

    private String host;

    private String supervision;

    private String price;

    private String story;

    @Column(name = "is_visited")
    private boolean isVisited;

    @Column(name = "is_with_child")
    private boolean isWithChild;

    @Column(name = "is_univ_road")
    private boolean isUnivRoad;

    @Column(name = "is_festival")
    private boolean isFestival;

    @Column(name = "is_musical_license")
    private boolean isMusicalLicense;

    @Column(name = "is_musical_create")
    private boolean isMusicalCreate;

    private String time;

}