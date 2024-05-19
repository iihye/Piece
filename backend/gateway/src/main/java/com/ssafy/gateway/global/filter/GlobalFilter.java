package com.ssafy.gateway.global.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 모든 요청의 jwt 정보를 통해 사용자 정보를 서비스들에 전달시켜주는 필터. 사용자 정보는 헤더에 담아서 전달. 추후 jwt인가 로직이 구현 완료되면 세부사항 수정할것
 */
@Component
@Slf4j

public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("Authenticated-User-Header", "1")
                .build();
            if (config.isPreLogger()) {
                log.info("Request ID: {}", modifiedRequest.getId());
            }

            return chain.filter(exchange.mutate().request(modifiedRequest).build())
                .then(Mono.fromRunnable(() -> {
                    if (config.isPostLogger()) {
                        log.info("Response Status Code: {}",
                            exchange.getResponse().getStatusCode());
                    }
                }));
        };
    }

    @Getter
    @Setter
    public static class Config {

        private boolean preLogger;
        private boolean postLogger;
    }
}
