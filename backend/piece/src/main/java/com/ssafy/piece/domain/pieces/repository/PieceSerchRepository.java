package com.ssafy.piece.domain.pieces.repository;

import com.ssafy.piece.domain.pieces.entity.Pieces;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PieceSerchRepository extends JpaRepository<Pieces, Long> {

    @Query("select p from Pieces p where "+
        "p.openYn='Y' AND "+
        "(p.title like concat('%', :keyword, '%') OR "+
        "p.comment like concat('%', :keyword, '%') OR "+
        "p.record like concat('%', :keyword, '%'))")
    List<Pieces> findBykeyword(String keyword);

    @Query("select p from Pieces p where "+
        "(p.userId=:userId) AND "+
        "(p.title like concat('%', :keyword, '%') OR "+
        "p.comment like concat('%', :keyword, '%') OR "+
        "p.record like concat('%', :keyword, '%'))")
    List<Pieces> findBykeywordAndUserId(String keyword,Long userId);

}
