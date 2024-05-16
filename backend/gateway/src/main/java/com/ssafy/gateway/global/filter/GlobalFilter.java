package com.ssafy.gateway.global.filter;

import com.ssafy.gateway.global.JwtTokenUtil;
import lombok.Getter;
import lombok.Setter;
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
import org.springframework.web.reactive.socket.client.TomcatWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 모든 요청의 jwt 정보를 통해 사용자 정보를 서비스들에 전달시켜주는 필터. 사용자 정보는 헤더에 담아서 전달. 추후 jwt인가 로직이 구현 완료되면 세부사항 수정할것
 */
@Component
@Slf4j

public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // JWT 유틸리티 클래스를 주입받습니다.

    public GlobalFilter() {
        super(Config.class); // 부모 클래스의 생성자를 호출하여 Config 클래스를 사용합니다.
    }

        @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String authHeader = request.getHeaders().getFirst("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) { // "Bearer " 접두어를 제거하여 순수 토큰을 얻습니다.
                String jwtToken = authHeader.substring(7);
                log.info("JWT Token: {}", jwtToken);
                try {
                    String userId = jwtTokenUtil.getUserIdFromToken(jwtToken);// 토큰으로부터 사용자 ID를 추출합니다.
                    log.info("User ID: {}", userId);
                    ServerHttpRequest modifiedRequest = request.mutate()
                        .header("Authenticated-User-Header", userId.toString()) // 추출된 사용자 ID를 새 헤더에 추가합니다.
                        .build();
                    return chain.filter(exchange.mutate().request(modifiedRequest).build()); // 수정된 요청으로 체인을 계속합니다
                } catch (Exception e) {
                    log.error("Invalid JWT Token: {}", e.getMessage());
                    return onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED);
                }
            }

            return chain.filter(exchange); // JWT 토큰이 없거나 올바르지 않으면 원본 요청으로 체인을 계속합니다.
        };
    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            String authHeader = request.getHeaders().getFirst("Authorization"); // Authorization 헤더에서 JWT 토큰을 추출합니다.
//
//
//            if (authHeader != null && authHeader.startsWith("Bearer ")) { // "Bearer " 접두어를 제거하여 순수 토큰을 얻습니다.
//                String jwtToken = authHeader.substring(7);
//                try {
//                    Long userId = jwtTokenUtil.getUserIdFromToken(jwtToken);// 토큰으로부터 사용자 ID를 추출합니다.
//                    ServerHttpRequest modifiedRequest = request.mutate()
//                        .header("Authenticated-User-Id", userId.toString()) // 추출된 사용자 ID를 새 헤더에 추가합니다.
//                        .build();
//                    return chain.filter(exchange.mutate().request(modifiedRequest).build()); // 수정된 요청으로 체인을 계속합니다
//                } catch (Exception e) {
//                    log.error("Invalid JWT Token: {}", e.getMessage());
//                    return onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED);
//                }
//            }
//
//            return chain.filter(exchange); // JWT 토큰이 없거나 올바르지 않으면 원본 요청으로 체인을 계속합니다.
//        };
//    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }



//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                .header("Authenticated-User-Header", "1")
//                .build();
//            if (config.isPreLogger()) {
//                log.info("Request ID: {}", modifiedRequest.getId());
//            }
//
//            return chain.filter(exchange.mutate().request(modifiedRequest).build())
//                .then(Mono.fromRunnable(() -> {
//                    if (config.isPostLogger()) {
//                        log.info("Response Status Code: {}",
//                            exchange.getResponse().getStatusCode());
//                    }
//                }));
//        };
//    }

    @Bean
    @Primary
    WebSocketClient tomcatWebSocketClient() {
        return new TomcatWebSocketClient();
    }
    @Bean
    @Primary
    public RequestUpgradeStrategy requestUpgradeStrategy() {
        return new TomcatRequestUpgradeStrategy();
    }
    
    @Getter
    @Setter
    public static class Config {

        private boolean preLogger;
        private boolean postLogger;
    }
}
