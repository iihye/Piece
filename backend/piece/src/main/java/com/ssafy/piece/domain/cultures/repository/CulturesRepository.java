package com.ssafy.piece.domain.cultures.repository;

import com.ssafy.piece.domain.cultures.entity.Cultures;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CulturesRepository extends JpaRepository<Cultures, Long> {

    public List<Cultures> findByTitleContaining(String keyword);

}
