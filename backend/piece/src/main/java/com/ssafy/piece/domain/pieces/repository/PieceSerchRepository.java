package com.ssafy.piece.domain.pieces.repository;

import com.ssafy.piece.domain.pieces.entity.Pieces;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PieceSerchRepository extends JpaRepository<Pieces, Long> {

    @Query("select p from Pieces p where"+
        " (p.title LIKE %:keyword% )OR"+
        "(p.comment LIKE %:keyword%) OR"+
        "(p.record LIKE %:keyword%)")
    List<Pieces> findBykeyword(String keyword);

    @Query("select p from Pieces p where"+
        "(p.userId=:userId)OR"+
        " (p.title LIKE %:keyword% )OR"+
        "(p.comment LIKE %:keyword%) OR"+
        "(p.record LIKE %:keyword%)")
    List<Pieces> findBykeywordAndUserId(String keyword,Long userId);

}
