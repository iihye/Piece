package com.ssafy.piece.domain.reports.repository;

import com.ssafy.piece.domain.reports.entity.ReportsComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsCommentsRepository extends JpaRepository<ReportsComments, Long> {
}
