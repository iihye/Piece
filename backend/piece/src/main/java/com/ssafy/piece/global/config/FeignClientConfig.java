package com.ssafy.piece.global.config;

import com.ssafy.piece.domain.cultures.dto.response.KobisResponse;
import com.ssafy.piece.domain.cultures.dto.response.TmdbResponse;
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
                Object decodedObject = super.decode(response, type);

                if (decodedObject instanceof KobisResponse) {
                    // 올바르게 'movieList'를 추출하려면 'movieListResult' 객체에서 'getMovieList()' 메서드를 호출해야 합니다.
                    return ((KobisResponse) decodedObject);
                } else if (decodedObject instanceof TmdbResponse) {
                    return ((TmdbResponse) decodedObject);
                } else if (decodedObject instanceof SuccessResponse) {
                    // 여기에서 SuccessResponse 데이터를 추출합니다.
                    return ((SuccessResponse<?>) decodedObject).getData();
                }
                return decodedObject;
            }
        };
    }
}
