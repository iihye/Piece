package com.ssafy.user.entity;


import com.ssafy.user.global.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Builder
@NoArgsConstructor  // Lombok에 의해 기본 생성자를 추가
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 추가 (Builder와 함께 사용)
@Table(name = "users")
@Setter
@Getter
public class Users extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 255, nullable = true)
    private String profileImage;

    @Column(nullable = false, length = 10)
    private String nickname; //최대 10자

    @Column(nullable = false)
    private boolean isTutorial; //true: 보여준다, false: 안보여준다

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = true)
    private Long labelId;

    @Column(nullable = false, length = 255)
    private String  password;
}

