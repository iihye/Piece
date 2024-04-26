package com.ssafy.piece.domain.pieces.entity;

import lombok.Getter;

@Getter
public enum GenreType {
    FEAR(1),
    ROMANCE(2),
    COMEDY(3),
    CRIMEANDTHRILLER(4),
    ANIMATED(5),
    ACTION(6),
    FANTASYANDSF(7),
    DRAMA(8),
    MUSIC(9),
    WAR(10),
    HISTORY(11),
    SPORTS(12),
    CALAMITY(13),
    DOCUMENTARY(14),
    ETC(15);

    private final int id;

    GenreType(int id) {
        this.id = id;
    }
}