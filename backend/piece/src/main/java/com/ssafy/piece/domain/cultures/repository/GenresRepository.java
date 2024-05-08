package com.ssafy.piece.domain.cultures.repository;

import com.ssafy.piece.domain.cultures.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Long> {

}
