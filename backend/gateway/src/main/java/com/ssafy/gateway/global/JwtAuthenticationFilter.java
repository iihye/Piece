package com.ssafy.gateway.global;

import com.ssafy.gateway.global.JwtAuthenticationFilter.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                .header("Authenticated-User-Header", "1")
//                .build();
//
//            return chain.filter(exchange.mutate().request(modifiedRequest).build())
//                .then(Mono.fromRunnable(() -> {
//                    log.info("---JwtAuthenticationFilter is working---");
//                }));
//        };
//    }


    @Override
    public GatewayFilter apply(Config config) {
        log.info("---JwtAuthenticationFilter is working---");
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = request.getHeaders().getFirst("Authorization");
            log.info("Authorization Header: {}", token);

            if (token != null && token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                log.info("JWT Token: {}", jwtToken);
                try {
                    Long userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
                    if (userId != null) {
                        log.info("User ID: {}", userId);
                        // 사용자 ID를 커스텀 헤더에 추가
                        ServerHttpRequest modifiedRequest = request.mutate()
                            .header("Authorized-User-ID", userId.toString())
                            .build();
                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    }
                } catch (Exception e) {
                    log.error("JWT validation error: {}", e.getMessage());
                    return this.onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED, e.getMessage());
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus, String detailedError) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Error-Details", detailedError); // 오류 상세 정보 추가
        return response.setComplete();
    }

    public static class Config {
        // Configuration properties if required
    }
}
