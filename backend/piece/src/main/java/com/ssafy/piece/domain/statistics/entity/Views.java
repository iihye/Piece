package com.ssafy.piece.domain.statistics.entity;

import com.ssafy.piece.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Views {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long viewId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users userId;
    @Column(nullable = false)
    private int viewYear;
    private int movieNumber;
    private int dramaNumber;
    private int musicalNumber;
    private int concertNumber;
    private int etcNumber;





}
