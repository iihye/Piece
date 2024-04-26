package com.ssafy.piece.global.config;

import com.ssafy.piece.global.response.structure.SuccessResponse;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {

    @Bean
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringDecoder(messageConverters) {
            @Override
            public Object decode(Response response, Type type) throws IOException {
                // decode를 호출하여 SpringDecoder가 처리한 결과를 가져옴
                Object decodedObject = super.decode(response, type);

                // decodedObject가 SuccessResponse의 인스턴스인 경우 data 필드를 반환
                if (decodedObject instanceof SuccessResponse) {
                    return ((SuccessResponse<?>) decodedObject).getData();
                }

                // 그렇지 않은 경우 원래의 디코딩된 객체를 반환
                return decodedObject;
            }
        };
    }
}
