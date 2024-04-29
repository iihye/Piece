package com.ssafy.user.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;


@Data
@Entity
@Builder
@Table(name = "users")

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 255, nullable = true)
    private String profileImage;

    @Column(nullable = false , length = 10)
    private String nickname; //최대 10자

    @Column(nullable = false)
    private Timestamp createdAt ;

    @Column(nullable = false)
    private Timestamp updatedAt ;

    @Column(nullable = false)
    private Boolean isTutorial; //true: 보여준다, false: 안보여준다

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private Long labelId;

    @Column(nullable = false, length = 10)
    private String username; //로그인ID, 최대10자

    @Column(nullable = false, length = 255)
    private String password;
}
