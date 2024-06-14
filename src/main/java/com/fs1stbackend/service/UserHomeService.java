package com.fs1stbackend.service;

import com.fs1stbackend.repository.UserHomeRepository;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class UserHomeService {

    @Autowired
    private UserHomeRepository userHomeRepository;

    public String getUserAtHome(@RequestHeader("Authorization") String authorizationHeader) {
        String token = null;
        String userEmail = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

            try{
                if(JwtTokenUtility.validateToken(token)) {
                    Claims claims = JwtTokenUtility.extractClaims(token);
                    userEmail = claims.getSubject();

                } else {
                    return "토큰이 유효하지 않습니다";
                }
            } catch (Exception e) {
//                throw new Exception("서비스 레이어에서 getUserHome 메서드 실행 중 예외 발생했습니다");
            }

        }
    }


}
