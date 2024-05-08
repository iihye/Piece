package com.ssafy.piece.domain.statistics.repository;

import com.ssafy.piece.domain.statistics.entity.Views;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewsRepository extends JpaRepository<Views, Long> {

    Views findByUserIdAndViewYear(Long userId, int year);


}
