package com.ssafy.piece.domain.pieces.repository;

import com.ssafy.piece.domain.pieces.entity.Piecesheart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HeartRepository extends JpaRepository<Piecesheart, Long> {

    @Query("select count(h) from Piecesheart h where h.userId = :userId and h.piece.pieceId = :pieceId")
    Integer findByPieceIdAndUserId(Long pieceId, Long userId);

    @Query("select count(h) from Piecesheart h where h.piece.pieceId = :pieceId")
    Integer countByPieceId(Long pieceId);

    @Query("select h.heartId from Piecesheart h where h.userId = :userId and h.piece.pieceId = :pieceId")
    Long findByUserIdAndHeartId(Long userId, Long pieceId);

    @Modifying
    @Query("delete from Piecesheart h where h.piece.pieceId = :pieceId")
    void deleteByPieceId(Long pieceId);
}
