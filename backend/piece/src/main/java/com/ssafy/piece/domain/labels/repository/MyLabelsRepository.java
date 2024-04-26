package com.ssafy.piece.domain.labels.repository;

import com.ssafy.piece.domain.labels.entity.MyLabels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyLabelsRepository extends JpaRepository<MyLabels, Long> {

    @Query("select case when count(m) > 0 then true else false end from MyLabels m where m.labels.labelId = :labelId and m.users.userId = :userId")
    Boolean existsByLabelIdAndUserId(Long labelId, Long userId);

}
