package com.ssafy.piece.domain.reports.repository;

import com.ssafy.piece.domain.reports.entity.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportsRepository extends JpaRepository<Reports, Long> {
}
