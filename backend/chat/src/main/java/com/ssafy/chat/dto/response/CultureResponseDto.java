package com.ssafy.chat.dto.response;

import com.ssafy.chat.entity.CultureType;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CultureResponseDto {
    private Long cultureId;

    private CultureType cultureType;

    private String code;

    private String title;

    private String imageUrl;

    private List<Long> genreIdList;
}
