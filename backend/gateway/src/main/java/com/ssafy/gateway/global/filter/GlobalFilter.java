package com.ssafy.gateway.global.filter; // 패키지 선언

import com.ssafy.gateway.global.JwtTokenUtil; // JwtTokenUtil 클래스를 import합니다.
import lombok.extern.slf4j.Slf4j; // Slf4j 로깅 라이브러리를 사용하기 위한 어노테이션 import
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired; // Spring의 Autowired 어노테이션을 import합니다.
import org.springframework.cloud.gateway.filter.GatewayFilter; // Spring Cloud GatewayFilter를 import합니다.
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory; // GatewayFilterFactory 클래스를 import합니다.
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component // 이 클래스를 Spring 컨테이너가 관리하는 컴포넌트로 선언합니다.
@Slf4j // 로그를 기록하기 위한 Slf4j 사용하도록 설정합니다.
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> { // GatewayFilter의 설정을 확장하는 클래스 정의

    @Autowired // 자동으로 의존성을 주입받기 위해 Autowired 어노테이션 사용
    private JwtTokenUtil jwtTokenUtil; // JwtTokenUtil의 인스턴스를 주입받습니다.

    public GlobalFilter() {
        super(Config.class); // 부모 클래스의 생성자 호출, 필터의 설정 타입을 명시합니다.
    }

    @Override
    public GatewayFilter apply(Config config) { // 필터 로직을 정의하는 메서드
        return (exchange, chain) -> { // GatewayFilter 인터페이스의 apply 메서드를 람다식으로 구현
            ServerHttpRequest request = exchange.getRequest(); // 현재 요청을 가져옵니다.
            String path = request.getURI().getPath();
            log.info("path : {} " , path);
            String authorizationHeader = request.getHeaders().getFirst("Authorization"); // Authorization 헤더를 추출합니다.

            log.info("게이트웨이에서 요청 받음. Authorization 헤더: {}", authorizationHeader); // 헤더 받는 로그


            // 로그인, 회원가입, 닉네임-이메일 중복 검사 요청 등에 대한 인증 필터 예외 처리
            if (path.startsWith("/user/users/check-nickname") ||
                path.startsWith("/user/users/check-email") ||
                path.startsWith("/user/auth/login") ||
                path.startsWith("/user/users/register") ||
                path.startsWith("/user/users/email-validation") ||
                path.startsWith(("/user/users/find"))
            ) {

                log.info("이 로그가 보인다면 로그인,회원가입,중복검사 등에 대하여 인증을 예외하였음 : {}", path);
                return chain.filter(exchange);  // 인증 필터 없이 체인을 계속
            }



            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // Authorization 헤더가 Bearer 토큰을 포함하는지 검사합니다.
                String jwtToken = authorizationHeader.substring(7); // Bearer 다음에 오는 JWT 토큰을 추출합니다.
                log.info("헤더에서 JWT 추출 성공: {}", jwtToken); // JWT 추출 로그

                try {
                    Long userId = jwtTokenUtil.getUserIdFromToken(jwtToken); // 토큰에서 사용자 ID를 추출합니다.
                    log.debug("추출된 사용자 ID: {}", userId); // 로그에 사용자 ID를 기록합니다.

                    // 요청 헤더에 인증된 사용자 ID를 추가합니다.
                    ServerHttpRequest modifiedRequest = request.mutate()
                        .header("Authenticated-User-ID", userId.toString())
                        .build(); // 요청을 수정하여 새로운 Authorization 헤더로 사용자 ID를 포함시킵니다.
                    log.debug("새 헤더가 추가된 요청을 전달합니다."); // 수정된 요청에 대한 로그 기록

                    return chain.filter(exchange.mutate().request(modifiedRequest).build()); // 수정된 요청으로 다음 필터 체인을 계속합니다.
                } catch (Exception e) {
                    // JWT 토큰 처리 중 오류 발생 시 로그를 기록하고 401 Unauthorized 상태 코드를 반환합니다.
                    log.error("JWT 토큰 처리 중 오류 발생: {}", e.getMessage(), e); // 오류 로그 기록
                    return onError(exchange, "유효하지 않은 JWT 토큰", HttpStatus.UNAUTHORIZED); // 오류 응답 반환
                }
            } else {

                // Authorization 헤더가 없거나 Bearer 타입이 아닐 경우 경고 로그를 남깁니다.
                log.warn("Authorization 헤더가 없거나 Bearer 문자열로 시작하지 않습니다."); // 경고 로그 기록
                return chain.filter(exchange); // 요청을 다음 필터 체인으로 전달합니다.
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) { // 오류 응답을 처리하는 메서드
        exchange.getResponse().setStatusCode(httpStatus); // HTTP 상태 코드 설정
        log.error("오류 상태 응답: {} - {}", httpStatus, errorMessage); // 오류 상태와 메시지 로그 기록
        return exchange.getResponse().setComplete(); // 응답 완료
    }

    public static class Config {
        // 필요한 설정이 있으면 추가할 수 있습니다.
    }
}
