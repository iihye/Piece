package com.ssafy.piece.domain.cultures.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "genres")
public class Genres {

    // 고유 코드로 id 관리하기 때문에 generate value 없음
    @Id
    @Column(name = "genre_id")
    private Long id;

    @Column(nullable = false)
    private String name;

}
