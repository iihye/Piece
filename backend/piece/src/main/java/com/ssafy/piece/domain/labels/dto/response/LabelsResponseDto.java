package com.ssafy.piece.domain.labels.dto.response;

import com.ssafy.piece.domain.labels.entity.LabelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelsResponseDto {

    private Long labelId;
    private LabelType labelType;
    private String title;
    private String description;
    private boolean isMyLabels;
    private boolean isWearLabels;
}
