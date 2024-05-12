package com.ssafy.user.service;


import com.ssafy.user.entity.Users;
import com.ssafy.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;



    public Users getUserById(Long userId) { // UserId로 회원을 찾기.
        return usersRepository.findByUserId(userId);
    }
}
