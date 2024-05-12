package com.ssafy.user.global.security.config;

import com.ssafy.user.global.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ssafy.user.global.security.JwtRequestFilter;




@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //필터체인: 모든 유저의 요청과 서버의 응답 사이에 자동으로 실행해주고 싶은 코드를 담는 곳

    private final JwtRequestFilter jwtRequestFilter;

    // 의존성 주입을 위한 생성자
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/login", "/users/register").permitAll()  // 인증이 필요없는 경로
                .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 세션 사용하지 않음
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // JWT 필터 추가

        return http.build();
    }






//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf((csrf) -> csrf.disable())
//            .authorizeHttpRequests((authorize) -> authorize
//                .requestMatchers("/api/auth/**").permitAll()  // 로그인, 회원가입 등 인증이 필요없는 경로
//                .anyRequest().authenticated()           // 그 외 모든 요청은 인증 필요
//            )
//            .formLogin((formLogin) -> formLogin
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .permitAll()
//            )
//            .logout(logout -> logout
//                .logoutUrl("/api/logout")
//                .permitAll()
//            )
//            .sessionManagement((session) -> session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용하지 않음
//            )
//            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가
//
//        return http.build();
//    }
}