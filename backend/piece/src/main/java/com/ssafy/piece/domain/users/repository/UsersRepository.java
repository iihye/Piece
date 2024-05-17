package com.ssafy.piece.domain.users.repository;

import com.ssafy.piece.domain.users.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    //기본적인 CRUD 기능은 따로 메소드 작성하지 않아도 괜찮다.
    boolean existsByEmail(String email); // 이메일 중복 검사를 위한 메소드
    boolean existsByNickname(String nickname); // 닉네임 중복 검사를 위한 메소드

    Users findByEmail(String email); // 이메일로 사용자 조회

    @Query("select u from Users u where u.userId = :userId")
    Optional<Users> findByUserId(Long userId);

}
