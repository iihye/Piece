package com.ssafy.piece.domain.pieces.repository;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.pieces.entity.GenreType;
import com.ssafy.piece.domain.pieces.entity.Pieces;
import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PiecesRepository extends JpaRepository<Pieces, Long> {


    @Query("select p from Pieces p where p.openYn = 'Y'")
    List<Pieces> findByAll();

    @Query("select p from Pieces p where p.user.userId = :userId")
    List<Pieces> findByUserId(Long userId);

    @Query("select p from Pieces p order by p.date desc")
    Page<Pieces> findTopPieces(Pageable pageable);

    @Query("select p from Pieces p where p.user.userId = :userId and p.createdAt BETWEEN :oneYearAgoStart AND :oneYearAgoEnd")
    Optional<Pieces> findByPieceIdAndCreatedAt(Long userId, LocalDateTime oneYearAgoStart,
        LocalDateTime oneYearAgoEnd);

    // 칭호 관련
    @Query("select count(p) from Pieces p where p.genre = :genre and p.user.userId = :userId")
    int countByGenre(GenreType genre, Long userId);

    @Query("select count(p) from Pieces p where p.performanceType = :performanceType and p.user.userId = :userId")
    int countByPerformanceType(CultureType performanceType, Long userId);

    @Query("select count(p) from Pieces p where p.performanceType = :performanceType and p.time between :start and :end and p.user.userId = :userId")
    int countByTimeMovie(LocalTime start, LocalTime end,
        CultureType performanceType, Long userId);

    @Query("select count(p) from Pieces p where p.seat like %:seat% and p.user.userId = :userId")
    int countBySeat(String seat, Long userId);

    @Query("select p.cast, count(p) from Pieces p where p.performanceType = :performanceType and p.user.userId = :userId group by p.cast")
    List<Tuple> countPiecesByCastForUser(CultureType performanceType, Long userId);

    @Query("select count(p) from Pieces p where p.user.userId = :userId")
    int countByUserId(Long userId);

    @Query("select count(p) from Pieces p where p.createdAt between :start and :end and p.user.userId = :userId")
    int findPiecesByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Long userId);

    @Query("select count(p) from Pieces p where DATE(p.createdAt) = p.date and p.user.userId = :userId")
    int findPiecesByDateAndCreatedAt(Long userId);

    @Query("select count(p.price) from Pieces p where p.user.userId = :userId")
    int sumByUserId(Long userId);

    @Query("select p.genre from Pieces p group by p.genre")
    Set<String> countByGenreType();
}
