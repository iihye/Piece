package com.ssafy.piece.domain.pieces.entity;

import com.ssafy.piece.domain.users.entity.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "piecesheart")
public class Piecesheart {

    @Id
    private Long heartId;

    @ManyToOne
    private Pieces piece;

    @ManyToOne
    private Users user;
}
