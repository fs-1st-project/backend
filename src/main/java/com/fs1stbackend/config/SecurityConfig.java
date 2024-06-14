package com.fs1stbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                    .cors(cors -> cors.disable()) // CORS 보호 비활성화
                    .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests
                                    .requestMatchers("/register").permitAll()// 회원가입 엔드포인트에 대한 접근 허용
                                    .requestMatchers("/auth/login").permitAll()
                                    .requestMatchers("/home/user").permitAll()
                                    .requestMatchers("/firebase/auth/google").permitAll() //구글 로그인 엔드포인트에 대한 접근 허용
                                    .requestMatchers("/firebase/auth/user/**").permitAll() //받은 uid 로그인 엔드포인트에 대한 접근 허용
                                    .requestMatchers("/api/users/{uid}/profile").permitAll()
                                    .anyRequest().authenticated()// 그 외의 모든 요청은 인증 필요
                    );

            return http.build();
    }
}


