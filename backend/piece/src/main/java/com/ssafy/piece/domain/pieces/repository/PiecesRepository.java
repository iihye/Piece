package com.ssafy.piece.domain.pieces.repository;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.pieces.entity.GenreType;
import com.ssafy.piece.domain.pieces.entity.Pieces;
import jakarta.persistence.Tuple;
import java.time.LocalDate;
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

    @Query("select p from Pieces p where p.userId = :userId")
    List<Pieces> findByUserId(Long userId);

    @Query("select p from Pieces p join Piecesheart h on p.pieceId = h.piece.pieceId where h.userId = :userId")
    List<Pieces> findByUserIdAndHeart(Long userId);

    @Query("select p from Pieces p order by p.date desc")
    Page<Pieces> findTopPieces(Pageable pageable);

    @Query("select p from Pieces p where p.userId = :userId and p.createdAt BETWEEN :oneYearAgoStart AND :oneYearAgoEnd")
    Optional<Pieces> findByPieceIdAndCreatedAt(Long userId, LocalDateTime oneYearAgoStart,
        LocalDateTime oneYearAgoEnd);

    List<Pieces> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    // 칭호 관련
    @Query("select count(p) from Pieces p where p.genre = :genre and p.userId = :userId")
    int countByGenre(GenreType genre, Long userId);

    @Query("select count(p) from Pieces p where p.performanceType = :performanceType and p.userId = :userId")
    int countByPerformanceType(CultureType performanceType, Long userId);

    @Query("select count(p) from Pieces p where p.performanceType = :performanceType and p.time between :start and :end and p.userId = :userId")
    int countByTimeMovie(LocalTime start, LocalTime end,
        CultureType performanceType, Long userId);

    @Query("select count(p) from Pieces p where p.seat like %:seat% and p.userId = :userId")
    int countBySeat(String seat, Long userId);

    @Query("select p.cast, count(p) from Pieces p where p.performanceType = :performanceType and p.userId = :userId group by p.cast")
    List<Tuple> countPiecesByCastForUser(CultureType performanceType, Long userId);

    @Query("select count(p) from Pieces p where p.userId = :userId")
    int countByUserId(Long userId);

    @Query("select count(p) from Pieces p where p.createdAt between :start and :end and p.userId = :userId")
    int findPiecesByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Long userId);

    @Query("select count(p) from Pieces p where DATE(p.createdAt) = p.date and p.userId = :userId")
    int findPiecesByDateAndCreatedAt(Long userId);

    @Query("select sum(p.price) from Pieces p where p.userId = :userId")
    int sumByUserId(Long userId);

    @Query("select p.genre from Pieces p where p.userId = :userId group by p.genre")
    Set<String> countByGenreType(Long userId);
}
