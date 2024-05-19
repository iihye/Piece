//package com.ssafy.user.global.security;
//
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TokenBlacklistService { //토큰을 무효화합니다.
//    private Map<String, Boolean> tokenBlacklist = new ConcurrentHashMap<>();
//
//    public void blacklistToken(String token) {
//        tokenBlacklist.put(token, true);
//    }
//
//    public boolean isTokenBlacklisted(String token) {
//        return tokenBlacklist.getOrDefault(token, false);
//    }
//}
