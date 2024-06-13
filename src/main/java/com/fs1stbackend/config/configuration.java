package com.fs1stbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class configuration {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 허용할 Origin 설정
        config.addAllowedOrigin("*");

        // 허용할 HTTP 메서드 설정
        config.addAllowedMethod("*");

        // 필요에 따라 허용할 헤더 설정
        config.addAllowedHeader("*");

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

