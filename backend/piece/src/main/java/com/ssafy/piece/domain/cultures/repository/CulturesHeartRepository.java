package com.ssafy.piece.domain.cultures.repository;

import com.ssafy.piece.domain.cultures.entity.CulturesHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturesHeartRepository extends JpaRepository<CulturesHeart, Long> {

    void deleteCulturesHeartByCultureIdAndUserId(Long cultureId, Long userId);

    Boolean existsCulturesHeartByCultureIdAndUserId(Long cultureId, Long userId);

    Long countCulturesHeartByCultureIdAndUserId(Long cultureId, Long userId);
}
