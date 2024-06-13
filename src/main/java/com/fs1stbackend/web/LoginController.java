package com.fs1stbackend.web;

import com.fs1stbackend.dto.LoginDTO;
import com.fs1stbackend.repository.LoginRepository;
import com.fs1stbackend.service.LoginService;
import com.fs1stbackend.service.SignupService;
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

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private LoginService loginService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        println("앞단과 연결이 되었습니다");
//        return loginService.loginUser(loginDTO);
        try {
            // 로그인 서비스를 통해 로그인 처리
            String token = loginService.loginUser(loginDTO);

            // 성공적으로 토큰을 받았을 경우, 토큰을 JSON 형태로 반환
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } catch (Exception e) {
            // 예외가 발생했을 경우, 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
