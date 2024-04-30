package com.ssafy.piece.domain.labels.entity;

import com.ssafy.piece.global.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "mylabels",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "labelId"})})
public class MyLabels extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myLabelId;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "labelId")
    private Labels labels;
}
