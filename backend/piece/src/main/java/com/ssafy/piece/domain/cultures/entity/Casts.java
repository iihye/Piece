package com.ssafy.piece.domain.cultures.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "casts")
@Getter
public class Casts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "casts_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culture_id")
    private Cultures cultures;

    private String name;

    private String image_url;

}
