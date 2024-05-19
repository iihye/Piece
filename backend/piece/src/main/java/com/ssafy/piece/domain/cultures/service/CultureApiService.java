package com.ssafy.piece.domain.cultures.service;

import com.ssafy.piece.domain.cultures.dto.response.CultureDetailResponse;
import com.ssafy.piece.domain.cultures.dto.response.CulturesResponseMapper;
import com.ssafy.piece.domain.cultures.dto.response.MovieResult;
import com.ssafy.piece.domain.cultures.dto.response.SimpleMovieResponse;
import com.ssafy.piece.domain.cultures.dto.response.TmdbDetailResponse;
import com.ssafy.piece.domain.cultures.dto.response.TmdbResponse;
import com.ssafy.piece.domain.cultures.dto.xml.KopisResponse;
import com.ssafy.piece.domain.cultures.entity.CultureGenre;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.domain.cultures.entity.Genres;
import com.ssafy.piece.domain.cultures.repository.CultureGenreRepository;
import com.ssafy.piece.domain.cultures.repository.CulturesRepository;
import com.ssafy.piece.domain.cultures.repository.GenresRepository;
import com.ssafy.piece.global.client.KopisClient;
import com.ssafy.piece.global.client.TmdbClient;
import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.EntityNotFoundException;
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
    private final KopisClient kopisClient;

    @Value("${secret.tmdb.api-key}")
    private String TMDB_API_KEY;

    @Value("${secret.kopis.api-key}")
    private String KOPIS_API_KEY;

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

    public CultureDetailResponse findMovie(String movieId) {
        TmdbDetailResponse movie = tmdbClient.getTmdbMovie(movieId, "Bearer " + TMDB_API_KEY);
        Cultures cultures = culturesRepository.findCulturesByCode(movie.getId().toString())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CAKE_NOT_FOUND));
        return CulturesResponseMapper.tmdbResponseToCultureDetailResponse(movie, cultures.getId());
    }

    public List<SimpleMovieResponse> searchMovie(String name) {
        TmdbResponse response = tmdbClient.searchTMDBMovie(name, "Bearer " + TMDB_API_KEY);
//        response.getResults().stream()
        List<MovieResult> list = response.getResults();
        if (list.size() > 5) {
            list.subList(5, list.size()).clear();
        }
        return CulturesResponseMapper.movieResultToSimpleMovieResponseList(list);
    }

    public CultureDetailResponse findConcert(String concertId) {
        KopisResponse data = kopisClient.getKopisData(concertId, KOPIS_API_KEY);
        Cultures cultures = culturesRepository.findCulturesByCode(data.getDb().getMt20id())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CAKE_NOT_FOUND));
        return CulturesResponseMapper.kopisResponseToCultureDetailResponse(data, cultures.getId());
    }
}
