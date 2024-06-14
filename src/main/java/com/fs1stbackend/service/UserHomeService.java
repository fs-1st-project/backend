package com.fs1stbackend.service;

import com.fs1stbackend.dto.UserAndUserProfileDTO;
import com.fs1stbackend.model.UserAndUserProfile;
import com.fs1stbackend.repository.UserHomeRepository;
import com.fs1stbackend.service.exception.InvalidTokenException;
import com.fs1stbackend.service.exception.UserNotFoundException;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Service
public class UserHomeService {

    @Autowired
    private UserHomeRepository userHomeRepository;

    public UserAndUserProfileDTO findUserProfileAtHome(@RequestHeader("Authorization") String authorizationHeader) {
        String token = null;
        String userEmail = null;
        UserAndUserProfileDTO userAndUserProfileDTO = null;

        // 토큰을 받았는지 확인
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

                // 토큰이 유효하면 토큰 parse해서 해당 유저 정보 얻기
                if(JwtTokenUtility.validateToken(token)) {
                    Claims claims = JwtTokenUtility.extractClaims(token);
                    userEmail = claims.getSubject();

                    UserAndUserProfile user = userHomeRepository.findUserProfileAtHome(userEmail);

                    userAndUserProfileDTO = new UserAndUserProfileDTO(user);
                } else {
                    throw new InvalidTokenException("해당 토큰이 유효하지 않습니다");
                }

        }
        return userAndUserProfileDTO;
    }
}
