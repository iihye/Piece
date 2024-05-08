package com.ssafy.piece.domain.cultures.dto.response;

import com.ssafy.piece.domain.cultures.entity.CultureGenre;
import com.ssafy.piece.domain.cultures.entity.Cultures;
import com.ssafy.piece.domain.cultures.entity.Genres;
import java.util.List;
import java.util.stream.Collectors;

public class CulturesResponseMapper {

    public static CulturesResponse cultureEntityToDto(Cultures cultures) {
        return CulturesResponse.builder()
            .CultureId(cultures.getId())
            .code(cultures.getCode())
            .imageUrl(cultures.getImageUrl())
            .title(cultures.getTitle())
            .cultureType(cultures.getCultureType())
            .genreIdList(cultures.getCultureGenre()
                .stream().map(CultureGenre::getGenre).map(Genres::getId)
                .collect(Collectors.toList()))
            .build();
    }

    public static List<CulturesResponse> cultureEntityToDtoList(List<Cultures> cultures) {
        return cultures.stream()
            .map(CulturesResponseMapper::cultureEntityToDto)
            .collect(Collectors.toList());
    }
}
