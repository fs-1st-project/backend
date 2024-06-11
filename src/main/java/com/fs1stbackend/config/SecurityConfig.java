package com.fs1stbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 보호 비활성화 (필요한 경우)
                .authorizeRequests()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll() // Swagger UI에 대한 접근 허용
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll() // OpenAPI 문서에 대한 접근 허용
                .anyRequest().authenticated() // 다른 모든 요청은 인증이 필요
                .and()
                .formLogin() // 기본 폼 로그인 사용
                .permitAll()
                .and()
                .logout() // 기본 로그아웃 사용
                .permitAll();

        return http.build();
    }
}