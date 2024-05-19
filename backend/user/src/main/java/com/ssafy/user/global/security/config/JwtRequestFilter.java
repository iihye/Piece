package com.ssafy.user.global.security.config;

import com.ssafy.user.global.security.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        Long userId = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            log.info("JWT 토큰: {}", jwtToken);
            try {
                userId = jwtTokenUtil.getUserIdFromToken(jwtToken); // UserId를 Long 타입으로 추출
                log.info("사용자 ID: {}", userId);
            } catch (IllegalArgumentException e) {
                log.error("JWT 토큰을 가져올 수 없습니다.", e);
            } catch (ExpiredJwtException e) {
                log.error("JWT 토큰이 만료되었습니다.", e);
            } catch (JwtException e) {
                log.error("JWT 토큰 검증 실패", e);
            }
        } else {
            log.warn("JWT 토큰은 Bearer 문자열로 시작해야 합니다");
        }

        if (userId != null && jwtTokenUtil.validateToken(jwtToken, userId) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId.toString());
            log.info("사용자 정보: {}", userDetails.getUsername());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.info("사용자 인증 완료: {}", userDetails.getUsername());
        }

        chain.doFilter(request, response);
    }
}
