package com.ssafy.user.global.security;

import com.ssafy.user.entity.Users;
import com.ssafy.user.repository.UsersRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long userId;
        try {
            userId = Long.parseLong(username);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("유효하지 않은 사용자 ID 형식: " + username);
        }

        Users user = usersRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저 ID를 찾을 수 없습니다.: " + userId));

        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }


}
