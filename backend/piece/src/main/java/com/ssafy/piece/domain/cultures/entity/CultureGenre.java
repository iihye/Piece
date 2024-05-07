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
import lombok.Getter;

@Entity
@Getter
@Table(name = "culture_genre")
public class CultureGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "culture_genre_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culture_id", nullable = false)
    private Cultures culture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genres genre;

    public CultureGenre createCultureGenre(Cultures culture, Genres genre) {
        CultureGenre cultureGenre = new CultureGenre();
        cultureGenre.culture = culture;
        cultureGenre.genre = genre;
        return cultureGenre;
    }
}
