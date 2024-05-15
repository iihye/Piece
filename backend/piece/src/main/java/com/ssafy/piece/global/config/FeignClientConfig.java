package com.ssafy.piece.global.config;

import com.ssafy.piece.domain.cultures.dto.response.KobisResponse;
import com.ssafy.piece.domain.cultures.dto.response.TmdbDetailResponse;
import com.ssafy.piece.domain.cultures.dto.response.TmdbResponse;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import feign.Response;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FeignClientConfig {

    @Bean
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringDecoder(messageConverters) {
            @Override
            public Object decode(Response response, Type type) throws IOException {
                Object decodedObject = super.decode(response, type);

                if (decodedObject instanceof KobisResponse) {
                    return ((KobisResponse) decodedObject);
                } else if (decodedObject instanceof TmdbResponse) {
                    return ((TmdbResponse) decodedObject);
                } else if (decodedObject instanceof SuccessResponse) {
                    return ((SuccessResponse<?>) decodedObject).getData();
                } else if (decodedObject instanceof TmdbDetailResponse) {
                    log.info("get TmdbDetailResponse : {}", decodedObject);
                    return ((TmdbDetailResponse) decodedObject);
                }
                return decodedObject;
            }
        };
    }
}
