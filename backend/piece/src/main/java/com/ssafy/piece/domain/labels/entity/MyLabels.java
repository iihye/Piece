package com.ssafy.piece.domain.labels.entity;

import com.ssafy.piece.domain.users.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "mylabels")
public class MyLabels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myLabelId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "labelId")
    private Labels labels;

    @Column(nullable = false)
    private LocalDate createdAt;
}
