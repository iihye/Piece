package com.ssafy.user.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

//    @Value("${jwt.secret}")
//    private String secretKey;
    public String secretKey = "VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHa"; // 환경 변수로 관리하는 것이 좋습니다.
    private final Key key;
    public JwtTokenUtil() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

//    public Long getUserIdFromToken(String token) {
//        Claims claims = Jwts.parser()
//            .setSigningKey(secretKey.getBytes())
//            .parseClaimsJws(token)
//            .getBody();
//        return Long.parseLong(claims.getSubject());
//    }
//
//    public String generateToken(String username) {
//        return Jwts.builder()
////            .setClaims()
//            .setSubject(username)
//            .setIssuedAt(new Date())
//            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 후 만료
//            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
//            .compact();
//    }



    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public String generateToken(Long userId) {
        return Jwts.builder()
//            .setClaims()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 후 만료
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }


    public boolean validateToken(String token, String username) {
        final String tokenUsername = getUsernameFromToken(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
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

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }
}

