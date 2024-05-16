package com.ssafy.piece.domain.pieces.repository;

import com.ssafy.piece.domain.pieces.entity.Piecesimage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PiecesimageRepository extends JpaRepository<Piecesimage, Long> {

    @Query("select p from Piecesimage p where p.piece.pieceId = :pieceId")
    List<Piecesimage> findByPieceId(Long pieceId);

    @Modifying
    @Query("delete from Piecesimage p where p.piece.pieceId = :pieceId")
    void deleteByPieceId(Long pieceId);

}
