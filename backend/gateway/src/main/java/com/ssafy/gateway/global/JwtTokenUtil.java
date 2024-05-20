
package com.ssafy.gateway.global;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenUtil {
    private final Key key;
    public String secretKey = "VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHa"; // 환경 변수로 관리하는 것이 좋습니다.

    public JwtTokenUtil() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            log.error("Token parsing failed: {}", e.getMessage());
            throw e; // 또는 적절한 예외 처리
        }
    }

    public String generateToken(Long userId) {
        return Jwts.builder()
            .setSubject(String.valueOf(userId)) // 'subject' 클레임: 사용자 ID
            .setIssuedAt(new Date()) // 'iat' 클레임: 토큰 발행 시간
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 'exp' 클레임: 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS256, key) // 서명 알고리즘과 키를 사용하여 서명
            .compact();
    }

    public boolean validateToken(String token, Long userId) {
        Long userIdFromToken = getUserIdFromToken(token);
        return (userId.equals(userIdFromToken) && !isTokenExpired(token));
    }

    private Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
