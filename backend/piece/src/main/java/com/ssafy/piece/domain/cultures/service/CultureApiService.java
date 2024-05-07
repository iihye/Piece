package com.ssafy.piece.domain.cultures.service;

import com.ssafy.piece.domain.cultures.dto.response.MovieResult;
import com.ssafy.piece.domain.cultures.dto.response.TmdbResponse;
import com.ssafy.piece.domain.cultures.entity.CultureGenre;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.domain.cultures.entity.Genres;
import com.ssafy.piece.domain.cultures.repository.CultureGenreRepository;
import com.ssafy.piece.domain.cultures.repository.CulturesRepository;
import com.ssafy.piece.domain.cultures.repository.GenresRepository;
import com.ssafy.piece.global.client.TmdbClient;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CultureApiService {

    private final TmdbClient tmdbClient;
    private final CulturesRepository culturesRepository;
    private final GenresRepository genresRepository;
    private final CultureGenreRepository cultureGenreRepository;

    @Value("${secret.tmdb.api-key}")
    private String TMDB_API_KEY;

    @Transactional
    public void collectMovieData() {
        for (int year = 2000; year <= 2023; year++) {
            for (int page = 1; page <= 500; page += 100) {
                fetchAndStoreMovies(year, page);
            }
        }
    }

    private void fetchAndStoreMovies(int year, int startPage) {
        log.info("{}년 {}페이지 저장", year, startPage);
        List<Cultures> culturesBatch = new ArrayList<>();
        List<CultureGenre> cultureGenreBatch = new ArrayList<>();

        for (int i = startPage; i < startPage + 100 && i <= 500; i++) {
            TmdbResponse tmdbResponse = tmdbClient.getTMDBMovieList(year + "-12-31",
                year + "-01-01", i, "Bearer " + TMDB_API_KEY);
            List<MovieResult> movieResultList = tmdbResponse.getResults();

            for (MovieResult result : movieResultList) {
                Cultures culture = new Cultures().createMovieCulture(result);
                culturesBatch.add(culture);

                result.getGenre_ids().forEach(genreId -> {
                    Genres genre = genresRepository.findById(Long.valueOf(genreId)).orElseThrow();
                    CultureGenre cultureGenre = new CultureGenre().createCultureGenre(culture,
                        genre);
                    cultureGenreBatch.add(cultureGenre);
                });
            }
        }

        culturesRepository.saveAll(culturesBatch);
        cultureGenreRepository.saveAll(
            cultureGenreBatch);
    }
}
