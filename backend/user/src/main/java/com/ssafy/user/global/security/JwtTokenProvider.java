//package com.ssafy.user.global.security;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import java.util.Date;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtTokenProvider { //사용자 인증
//    @Value("${app.jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${app.jwt.expiration-ms}")
//    private int jwtExpirationInMs;
//
//    public String generateToken(String username) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
//
//        return Jwts.builder()
//            .setSubject(username)
//            .setIssuedAt(now)
//            .setExpiration(expiryDate)
//            .signWith(SignatureAlgorithm.HS512, jwtSecret)
//            .compact();
//    }
//
//    public String getUsernameFromJWT(String token) {
//        Claims claims = Jwts.parser()
//            .setSigningKey(jwtSecret)
//            .parseClaimsJws(token)
//            .getBody();
//
//        return claims.getSubject();
//    }
//
//
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
//        } catch (Exception e) {
//
//        }
//        return false;
//    }
//}
