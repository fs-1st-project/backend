package com.fs1stbackend.web;

import com.fs1stbackend.dto.LoginDTO;
import com.fs1stbackend.dto.TokenAndUserDataDTO;
import com.fs1stbackend.service.LoginService;
import com.fs1stbackend.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<TokenAndUserDataDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        System.out.println("프론트와 연결 되었습니다");

            // 로그인 서비스에서 토큰과 유저 아이디 받아오기
            TokenAndUserDataDTO tokenAndUserIdDTO = loginService.loginUser(loginDTO);
            System.out.println("로그인 토큰이다!" + tokenAndUserIdDTO.getToken());

            // 성공적으로 토큰을 받았을 경우,  토큰과 유저 아이디 반환
            return ResponseEntity.ok().body(tokenAndUserIdDTO);
    }

}
