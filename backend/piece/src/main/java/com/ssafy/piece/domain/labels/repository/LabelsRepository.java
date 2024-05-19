package com.ssafy.piece.domain.labels.repository;

import com.ssafy.piece.domain.labels.entity.Labels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelsRepository extends JpaRepository<Labels, Long> {

}
