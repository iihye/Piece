package com.ssafy.piece.domain.labels.dto.request;

import com.ssafy.piece.domain.labels.entity.LabelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelsRequestDto {

    private LabelType labelType;
    private String title;
    private String description;
}
