package com.ssafy.piece.domain.cultures.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "casts")
@Getter
public class pupuCasts {

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
