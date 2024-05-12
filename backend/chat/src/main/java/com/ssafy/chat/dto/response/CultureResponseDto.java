package com.ssafy.chat.dto.response;

import com.ssafy.chat.entity.CultureType;
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
    Long id;
    CultureType cultureType;
    String title;
    String imageUrl;
}
