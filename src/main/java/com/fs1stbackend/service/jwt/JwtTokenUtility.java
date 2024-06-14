package com.fs1stbackend.service.jwt;

import com.fs1stbackend.dto.LoginDTO;
import io.jsonwebtoken.ExpiredJwtException;
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

    // JWT 검증 메서드
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 false 반환
            return false;
        } catch (Exception e) {
            // 그 외 예외 발생 시 false 반환
            return false;
        }
    }

    // JWT 만료 검사 후, 토큰 재생성
    public static String validateAndGenerateToken(String userEmail, String token) {
        // 토큰이 유효한 지, 검사
        if (token != null && validateToken(token)) {
            return token;
        } else {
            // 토큰이 만료 되었을 때, 토큰 재생성
            return generateToken(userEmail);
        }
    }
}
