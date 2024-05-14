package com.ssafy.piece.domain.cultures.repository;

import static com.ssafy.piece.domain.cultures.entity.QCultures.cultures;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.piece.domain.cultures.dto.response.CulturesResponse;
import com.ssafy.piece.domain.cultures.dto.response.CulturesResponseMapper;
import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.global.dto.PageResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CulturesQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Value("${base.service-url}")
    private String BASE_URL;

    /**
     * TODO
     * 장르, 페이징으로 필터를 걸어 동적으로 쿼리를 날리는 메서드
     * 검색어 필터도 추후 추가시키면 됨
     */
    @Transactional(readOnly = true)
    public PageResponse<CulturesResponse> findCultureList(CultureType cultureType,
        Long startPageId,
        int pageSize) {
        List<Cultures> list = queryFactory
            .select(cultures)
            .from(cultures)
            .where(
                ltCultureId(startPageId),
                equalCultureType(cultureType))
            .orderBy(cultures.id.desc())
            .limit(pageSize + 1)  // 다음페이지 존재하는지 확인하기 위해 +1
            .fetch();

        boolean hasNextPage = list.size() > pageSize;
        Long lastId = hasNextPage ? list.get(pageSize - 1).getId() : null;

        list = hasNextPage ? list.subList(0, pageSize) : list;
        list.stream()
            .map(Cultures::getCultureGenre)
            .forEach(Hibernate::initialize);
        List<CulturesResponse> responseList = list
            .stream()
            .map(CulturesResponseMapper::cultureEntityToDto)
            .collect(Collectors.toList());
        Map<String, String> queryParams = new HashMap<>();
        if (cultureType != null) {
            queryParams.put("cultureType", cultureType.toString());
        }

        return PageResponse.create(responseList, hasNextPage, BASE_URL, "cultures", lastId,
            pageSize, queryParams);
    }

    private BooleanExpression ltCultureId(Long cultureId) {
        log.info("ltCulturedId : {}", cultureId);
        return cultureId != null ? cultures.id.lt(cultureId) : null;
    }

    private BooleanExpression equalCultureType(CultureType cultureType) {
        log.info("equalCultureType : {}", cultureType);
        return cultureType != null ? cultures.cultureType.eq(cultureType) : null;
    }

}
