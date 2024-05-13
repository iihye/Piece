package com.ssafy.piece.domain.cultures.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class TmdbDetailResponse {

    private boolean adult;
    private String backdrop_path;
    private List<GenreDTO> genres;
    private Integer id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private Double popularity;
    private String poster_path;
    private String release_date;
    private Long revenue;
    private Integer runtime;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private Double vote_average;
    private Integer vote_count;
    private CreditDTO credits;

    @Getter
    public static class GenreDTO {

        private Integer id;
        private String name;
    }

    @Getter
    public static class CreditDTO {

        private List<CastDTO> cast;
    }

    @Getter
    public static class CastDTO {

        private boolean adult;
        private int gender;
        private Integer id;
        private String known_for_department;
        private String name;
        private String original_name;
        private Double popularity;
        private String profile_path;
        private Integer cast_id;
        private String character;
        private String credit_id;
        private Integer order;
    }
}


