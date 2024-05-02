package com.ssafy.piece.domain.cultures.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class KobisResponse {

    private MovieListResult movieListResult;

    @Getter
    public static class MovieListResult {

        private Integer totCnt;
        private String source;
        private List<Movie> movieList;
    }

    @Getter
    public static class Movie {

        private String movieCd;
        private String movieNm;
        private String movieNmEn;
        private String prdtYear;
        private String openDt;
        private String typeNm;
        private String prdtStatNm;
        private String nationAlt;
        private String genreAlt;
        private String repNationNm;
        private String repGenreNm;
        private List<Director> directors;
        private List<Company> companys;
    }

    @Getter
    public static class Director {

        private String peopleNm;
    }

    @Getter
    public static class Company {

        private String companyCd;
        private String companyNm;
    }
}

