package com.ssafy.user.global.client;

import com.ssafy.user.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 다른 마이크로 서비스 정보가 필요할 경우 
 * 해당 서비스로 rest api 요청을 보낼 수 있는 client 인터페이스
 * 구현체는 자동으로 생성되기 때문에 해당 인터페이스의 메서드를 사용하면 됨
 * @FeignClient의 name 속성은 다른 feign client 클래스와 겹치면 안됨
 * url 속성은 요청할 서비스의 url (config server의 각 설정파일의 external.~~ 속성 참고
 */
@FeignClient(name = "piece", url = "${external.piece.host}", configuration = FeignClientConfig.class)
public interface PieceClient {

}
