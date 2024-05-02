package com.ssafy.user.repository;

import com.ssafy.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    //기본적인 CRUD 기능은 따로 메소드 작성하지 않아도 괜찮다.
    boolean existsByEmail(String email); // 이메일 중복 검사를 위한 메소드
    boolean existsByNickname(String nickname); // 닉네임 중복 검사를 위한 메소드

}
