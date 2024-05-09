package com.ssafy.gateway.global;

import com.ssafy.gateway.global.JwtAuthenticationFilter.Config;
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
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = request.getHeaders().getFirst("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                try {
                    String userId = String.valueOf(jwtTokenUtil.getUserIdFromToken(jwtToken));
                    if (userId != null) {
                        ServerHttpRequest modifiedRequest = request.mutate()
                            .header("Authenticated-User-Id", userId)
                            .build();
                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    }
                } catch (Exception e) {
                    return this.onError(exchange, "Invalid JWT Token", HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    public static class Config {
        // Configuration properties if required
    }
}
