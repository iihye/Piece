package com.ssafy.piece.domain.cultures.service;

import com.ssafy.piece.domain.cultures.dto.response.CakeListResponseDto;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.domain.cultures.repository.CulturesRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CakeSearchService {

    private final CulturesRepository culturesRepository;

    // 공연 검색하기
    public List<CakeListResponseDto> searchCake(String keyword) {

        List<Cultures> list = culturesRepository.findByTitleContaining(keyword);

        List<CakeListResponseDto> result = new ArrayList<>();

        for (Cultures cake : list) {

            result.add(CakeListResponseDto.builder()
                .cultureId(cake.getId())
                .cultureType(cake.getCultureType())
                .title(cake.getTitle())
                .imageUrl(cake.getImageUrl())
                .state(cake.getState())
                .build());
        }

        return result;

    }
}
