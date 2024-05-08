package com.ssafy.piece.domain.cultures.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CulturesPageResponse {

    private List<CulturesResponse> culturesResponseList;

    private String nextPage;

}
