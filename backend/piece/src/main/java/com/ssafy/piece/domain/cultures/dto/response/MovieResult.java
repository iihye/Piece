package com.ssafy.piece.domain.cultures.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResult {

    private boolean adult;
    private String backdrop_path;
    private List<Integer> genre_ids;
    private String id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;

    private Credits credits;

    @Getter
    public static class Credits {

        List<Cast> cast;
    }

    @Getter
    public static class Cast {

        String name;
    }
}