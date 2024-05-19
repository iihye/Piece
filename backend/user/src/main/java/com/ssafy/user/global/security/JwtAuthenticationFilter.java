//package com.ssafy.user.global.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.GenericFilterBean;
//
//public class JwtAuthenticationFilter extends GenericFilterBean {
//    private JwtTokenProvider tokenProvider;
//
//    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider){
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//        throws IOException, ServletException {
//        String token = getTokenFromRequest((HttpServletRequest) request);
//        if (token != null && tokenProvider.validateToken(token)) {
//            String username = tokenProvider.getUsernameFromJWT(token);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null); // Authorities would be added here
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
