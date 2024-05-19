package com.ssafy.piece.domain.cultures.repository;

import com.ssafy.piece.domain.cultures.entity.Cultures;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturesRepository extends JpaRepository<Cultures, Long> {

    Optional<Cultures> findCulturesByCode(String code);
}
