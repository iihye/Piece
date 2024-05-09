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
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Users user = usersRepository.findById(Long.parseLong(id))
            .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저 id를 찾을 수 없습니다.: " + id));

        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

}
