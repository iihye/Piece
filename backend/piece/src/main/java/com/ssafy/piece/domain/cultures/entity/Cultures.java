package com.ssafy.piece.domain.cultures.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "cultures")
@Getter
public class Cultures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "culture_id")
    private Long id;

    @Column(name = "culture_type")
    @Enumerated(EnumType.STRING)
    private CultureType cultureType;

    private String code;

    private String title;

    private String imageUrl;

    private String genre;

    private String state;

    private String age;

    private String director;

    private String runtime;

}
