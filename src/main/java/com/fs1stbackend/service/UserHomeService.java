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

    public UserAndUserProfileDTO getUserAtHome(@RequestHeader("Authorization") String authorizationHeader) {
        String token = null;
        String userEmail = null;
        UserAndUserProfileDTO userAndUserProfileDTO = null;

        // 토큰을 받았는지 확인
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

            try{
                // 토큰이 유효하면 토큰 parse해서 해당 유저 정보 얻기
                if(JwtTokenUtility.validateToken(token)) {
                    Claims claims = JwtTokenUtility.extractClaims(token);
                    userEmail = claims.getSubject();
                    Optional<UserAndUserProfile> user = userHomeRepository.findUserProfileAtHome(userEmail);
                    System.out.println("데이터에서 유저를 조회한 후 받은 데이터" + user);

                    // userEmail로 repository에서 데이터와 비교 후 user 정보를 날려줬을 때
                    if(user.isPresent()) {
                        userAndUserProfileDTO = new UserAndUserProfileDTO(user.get());
                        System.out.println("데이터에서 Dto로 변환한 값" + userAndUserProfileDTO);
                    }

                } else {
                    throw new InvalidTokenException("해당 토큰이 유효하지 않습니다");
                }
            } catch (Exception e) {
                throw new UserNotFoundException("해당 토큰으로 유저를 찾을 수 없습니다");
            }

        }
        return userAndUserProfileDTO;
    }
}
