package com.fs1stbackend.service;

import com.fs1stbackend.dto.LoginDTO;
import com.fs1stbackend.model.User;
import com.fs1stbackend.repository.LoginRepository;
import com.fs1stbackend.service.exception.UserNotFoundException;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public String loginUser(@RequestBody LoginDTO loginDTO) {

        Optional<User> existingUserOptional = loginRepository.loginUser(loginDTO.getEmail(), loginDTO.getPassword());

        // db에 시도한 이메일과 패스워드를 가진 회원이 있다면, 토큰 생성 후 전달
        if(existingUserOptional.isPresent()) {
            return JwtTokenUtility.generateToken(loginDTO.getEmail());
        } else {
            throw new UserNotFoundException("데이터베이스에서 해당 유저를 찾을 수 없습니다");
        }
    }
}
