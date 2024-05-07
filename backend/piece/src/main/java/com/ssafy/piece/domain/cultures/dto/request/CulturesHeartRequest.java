package com.ssafy.piece.domain.cultures.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CulturesHeartRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long cultureId;

}
