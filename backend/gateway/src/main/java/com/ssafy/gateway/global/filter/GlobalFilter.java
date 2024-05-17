package com.ssafy.gateway.global.filter;

import com.ssafy.gateway.global.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            log.info("path : {} ", path);
            String authorizationHeader = request.getHeaders().getFirst("Authorization");

            log.info("게이트웨이에서 요청 받음. Authorization 헤더: {}", authorizationHeader);

            if (path.startsWith("/user/users/check-nickname") ||
                path.startsWith("/user/users/check-email") ||
                path.startsWith("/user/auth/login") ||
                path.startsWith("/user/users/register") ||
                path.startsWith("/user/users/email-validation") ||
                path.startsWith(("/user/users/find"))
            ) {
                log.info("이 로그가 보인다면 로그인,회원가입,중복검사 등에 대하여 인증을 예외하였음 : {}", path);
                return chain.filter(exchange);
            }

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwtToken = authorizationHeader.substring(7);
                log.info("헤더에서 JWT 추출 성공: {}", jwtToken);

                try {
                    Long userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
                    log.debug("추출된 사용자 ID: {}", userId);

                    ServerHttpRequest modifiedRequest = request.mutate()
                        .header("Authenticated-User-ID", userId.toString())
                        .build();
                    log.debug("새 헤더가 추가된 요청을 전달합니다.");

                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                } catch (Exception e) {
                    log.error("JWT 토큰 처리 중 오류 발생: {}", e.getMessage(), e);
                    return onError(exchange, "유효하지 않은 JWT 토큰", HttpStatus.UNAUTHORIZED);
                }
            } else {
                log.warn("Authorization 헤더가 없거나 Bearer 문자열로 시작하지 않습니다.");
                return chain.filter(exchange);
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        log.error("오류 상태 응답: {} - {}", httpStatus, errorMessage);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}
