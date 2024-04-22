package com.ssafy.piece.domain.users.repository;

import com.ssafy.piece.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    //기본적인 CRUD 기능은 따로 메소드 작성하지 않아도 괜찮다.

}
