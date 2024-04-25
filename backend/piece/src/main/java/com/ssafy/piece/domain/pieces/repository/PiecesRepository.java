package com.ssafy.piece.domain.pieces.repository;

import com.ssafy.piece.domain.pieces.entity.Pieces;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
}
