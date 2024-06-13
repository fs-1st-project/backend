package com.fs1stbackend.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtility {
    private static final String SECRET_KEY = "Linkedin"; // 이 시크릿 키는 보호해야 하는데, 어디로 넣을까요

    // JWT 토큰 생성 메서드
    public static String generateToken(String userEmail) {
        long expirationTimeMillis = 3600000; // 1시간
        Date expiryDate = new Date(System.currentTimeMillis() + expirationTimeMillis);

        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // JWT 검증하는 메서드
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
