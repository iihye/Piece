package com.ssafy.user.repository;

import com.ssafy.user.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    //기본적인 CRUD 기능은 따로 메소드 작성하지 않아도 괜찮다.
    boolean existsByEmail(String email); // 이메일 중복 검사를 위한 메소드
    boolean existsByNickname(String nickname); // 닉네임 중복 검사를 위한 메소드

    Optional<Users> findByEmail(String email); // 이메일로 사용자 조회

    Optional<Users> findByUserId(Long userId); //UserId로 회원을 조회한다.

//    @Query("select u from Users u where u.userId = :userId")
//    Optional<Users> findByUserId(Long userId);


    // profileImage 삭제
    @Modifying
    @Query("UPDATE Users u SET u.profileImage = NULL WHERE u.userId = :userId")
    void clearProfileImage(Long userId);
}