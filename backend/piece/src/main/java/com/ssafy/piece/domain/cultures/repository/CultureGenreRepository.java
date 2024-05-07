package com.ssafy.piece.domain.cultures.repository;

import com.ssafy.piece.domain.cultures.entity.CultureGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CultureGenreRepository extends JpaRepository<CultureGenre, Long> {

}
