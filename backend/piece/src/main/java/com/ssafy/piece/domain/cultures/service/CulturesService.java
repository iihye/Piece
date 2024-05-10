package com.ssafy.piece.domain.cultures.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.piece.domain.cultures.dto.response.CulturesResponse;
import com.ssafy.piece.domain.cultures.dto.response.CulturesResponseMapper;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.domain.cultures.entity.CulturesHeart;
import com.ssafy.piece.domain.cultures.exception.CakeNotFoundException;
import com.ssafy.piece.domain.cultures.repository.CulturesHeartRepository;
import com.ssafy.piece.domain.cultures.repository.CulturesRepository;
import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.EntityAlreadyExistException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CulturesService {

    private final CulturesRepository culturesRepository;
    private final CulturesHeartRepository culturesHeartRepository;

    private final JPAQueryFactory jpaQueryFactory;

    public void addCultureHeart(Long userId, Long cultureId) {
        Cultures culture = getCulture(cultureId);
        if (culturesHeartRepository.existsCulturesHeartByCultureIdAndUserId(userId, cultureId)) {
            throw new EntityAlreadyExistException(ErrorCode.CAKE_HEART_ALREADY_EXIST);
        }
        CulturesHeart culturesHeart = CulturesHeart.createCulturesHeart(culture, userId);
        culturesHeartRepository.save(culturesHeart);
    }

    public void removeCultureHeart(Long userId, Long cultureId) {
        culturesHeartRepository.deleteCulturesHeartByCultureIdAndUserId(cultureId, userId);
    }

    @Transactional(readOnly = true)
    public Boolean isCultureHeartExist(Long userId, Long cultureId) {
        return culturesHeartRepository.existsCulturesHeartByCultureIdAndUserId(userId, cultureId);
    }

    @Transactional(readOnly = true)
    public List<CulturesResponse> findCultureList() {
        List<Cultures> list = culturesRepository.findAll();
        return CulturesResponseMapper.cultureEntityToDtoList(list);
    }

    @Transactional(readOnly = true)
    public Long countCultureList(Long cultureId) {
        return culturesHeartRepository.countCulturesHeartByCultureId(cultureId);
    }

    public CulturesResponse findCulture(Long cultureId) {
        Cultures cultures = getCulture(cultureId);
        return CulturesResponseMapper.cultureEntityToDto(cultures);
    }

    // 케이크(공연, 영화 등 정보) 조회
    @Transactional(readOnly = true)
    public Cultures getCulture(Long cultureId) {
        return culturesRepository.findById(cultureId)
            .orElseThrow(CakeNotFoundException::new);
    }
}
