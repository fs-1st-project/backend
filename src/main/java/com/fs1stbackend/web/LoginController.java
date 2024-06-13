package com.fs1stbackend.web;

import com.fs1stbackend.dto.LoginDTO;
import com.fs1stbackend.repository.LoginRepository;
import com.fs1stbackend.service.LoginService;
import com.fs1stbackend.service.SignupService;
import com.fs1stbackend.service.jwt.JwtTokenUtility;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.logging.Logger;

import static java.sql.DriverManager.println;

@RestController
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        System.out.println("프론트와 연결 되었습니다");
        try {

            // 로그인 서비스에서 토큰 받아오기
            String token = loginService.loginUser(loginDTO);
            System.out.println(token);

            // 성공적으로 토큰을 받았을 경우, 토큰을 JSON 형태로 반환
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } catch (Exception e) {
            // 예외가 발생했을 경우, 500 Internal Server Error 반환
            System.out.println("예외 500 발생임");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
