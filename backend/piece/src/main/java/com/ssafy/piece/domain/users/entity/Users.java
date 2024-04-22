package com.ssafy.piece.domain.users.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.sql.Timestamp;
import lombok.Data;


@Data
@Entity
@Table(name = "users")

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; //사용자의 고유 식별자

    @Column(length = 255, nullable = false)
    private String email; // 사용자의 이메일입니다.

    @Column(length = 255, nullable = true)
    private String profileImage; //사용자의 프로필 이미지 URL

    @Column(nullable = false , length = 10)
    private String nickname; // 사용자 닉네임, 최대 10자

    @Column(nullable = false)
    private Timestamp createdAt ; //계정 생성 시각

    @Column(nullable = false)
    private Timestamp updatedAt ; //계정 정보 수정 시각

    @Column(nullable = false)
    private Boolean tutorial; //튜토리얼여부 (T: 보여준다 F:안보여준다.)

    @Column(nullable = false)
    private Long socialId; //소셜로그인 식별번호

    @Column(nullable = false)
    private Long labelId; // 사용자에게 부여된 칭호의 식별ID

    @Column(nullable = false, length = 10)
    private String username; //사용자의 로그인 아이디, 최대10자

    @Column(nullable = false, length = 255)
    private String password; //사용자의 로그인 비밀번호,길이는 해시 함수에 따라 달라질 수 있음
}
