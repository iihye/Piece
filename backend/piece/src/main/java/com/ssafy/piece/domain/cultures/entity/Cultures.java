package com.ssafy.piece.domain.cultures.entity;

import com.ssafy.piece.domain.cultures.dto.response.MovieResult;
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

//    private String state;

//    private String age;

//    private String director;

//    private String runtime;

    public Cultures createMovieCulture(MovieResult movieResult) {
        Cultures cultures = new Cultures();
        cultures.cultureType = CultureType.MOVIE;
        cultures.code = movieResult.getId();
        cultures.title = movieResult.getTitle();
        cultures.imageUrl = "https://image.tmdb.org/t/p/w400" + movieResult.getPoster_path();
        return cultures;
    }
}
