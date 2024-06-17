package com.fs1stbackend.service;

import com.fs1stbackend.dto.LoginDTO;
import com.fs1stbackend.dto.TokenAndUserIdDTO;
import com.fs1stbackend.model.User;
import com.fs1stbackend.repository.LoginRepository;
import com.fs1stbackend.service.exception.UserNotFoundException;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import static com.fs1stbackend.service.jwt.JwtTokenUtility.validateAndGenerateToken;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public TokenAndUserIdDTO loginUser(@RequestBody LoginDTO loginDTO) {

        Optional<User> existingUserOptional = loginRepository.loginUser(loginDTO.getEmail(), loginDTO.getPassword());

        // db에 시도한 이메일과 패스워드를 가진 회원이 있다면, 토큰 생성 후 전달
        if(existingUserOptional.isPresent()) {
            // 토큰 생성
            String token = JwtTokenUtility.generateToken(loginDTO.getEmail());

            // 갖고 있는 토큰이 있다면, 만료 되었는지 확인 및 토큰 재생성
            String finalToken = JwtTokenUtility.validateAndGenerateToken(loginDTO.getEmail(), token);

            Long existingUserId = existingUserOptional.get().getId();

            return new TokenAndUserIdDTO(finalToken, existingUserId);
        } else {
            throw new UserNotFoundException("데이터베이스에서 해당 유저를 찾을 수 없습니다");
        }
    }
}
