package com.ssafy.piece.domain.pieces.repository;

import com.ssafy.piece.domain.pieces.entity.Pieces;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PiecesRepository extends JpaRepository<Pieces, Long>{

}
