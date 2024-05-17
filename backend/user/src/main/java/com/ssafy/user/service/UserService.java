package com.ssafy.user.service;

import com.ssafy.user.entity.Users;
import com.ssafy.user.repository.UsersRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

<<<<<<< HEAD
    public Users getUserById(Long userId) { // UserId로 회원을 찾기.
        return usersRepository.findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저 이메일을 찾을 수 없습니다.: " + userId));
=======


    public Optional<Users> getUserById(Long userId) { // UserId로 회원을 찾기.
        return usersRepository.findByUserId(userId);


>>>>>>> 975cbdb334dae598122d1e2f46ad9fce34f50000
    }
}
