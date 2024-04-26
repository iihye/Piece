package com.ssafy.piece.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
            .allowedHeaders("Authorization", "Content-Type")
            .exposedHeaders("Set-Cookie", "access_token")
            .allowedOrigins("http://localhost:5173");
    }
}
