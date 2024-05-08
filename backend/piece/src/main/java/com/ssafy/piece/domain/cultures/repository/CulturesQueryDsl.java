package com.ssafy.piece.domain.cultures.repository;

import static com.ssafy.piece.domain.cultures.entity.QCultureGenre.cultureGenre;
import static com.ssafy.piece.domain.cultures.entity.QCultures.cultures;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.piece.domain.cultures.dto.response.CulturesResponse;
import com.ssafy.piece.domain.cultures.dto.response.CulturesResponseMapper;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.global.dto.PageResponse;
import java.util.List;
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
    public PageResponse<CulturesResponse> findCultureList(Long genreId, Long cultureId,
        int pageSize) {
        List<Cultures> list = queryFactory
            .select(cultures)
            .from(cultures)
            .where(
                ltCultureId(cultureId),
                equalGenreId(genreId))
            .orderBy(cultures.id.desc())
            .limit(pageSize + 1)  // Fetch one extra record to check for the next page
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

        return PageResponse.create(responseList, hasNextPage, BASE_URL, "cultures", lastId,
            pageSize);
    }

    private BooleanExpression ltCultureId(Long cultureId) {
        log.info("ltCulturedId : {}", cultureId);
        return cultureId != null ? cultures.id.lt(cultureId) : null;
    }

    private BooleanExpression equalGenreId(Long genreId) {
        return genreId != null ? cultureGenre.genre.id.eq(genreId) : null;
    }

}
