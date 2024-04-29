package com.ssafy.piece.domain.users.repository;

import com.ssafy.piece.domain.users.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("select u from Users u where u.userId = :userId")
    Optional<Users> findByUserId(Long userId);
}
