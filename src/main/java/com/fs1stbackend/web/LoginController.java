package com.fs1stbackend.web;

import com.fs1stbackend.dto.LoginDTO;
import com.fs1stbackend.repository.LoginRepository;
import com.fs1stbackend.service.LoginService;
import com.fs1stbackend.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class LoginController {

    @Autowired
    private LoginService LoginService;

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDTO loginDTO) {
        return LoginService.loginUser(loginDTO);
    }

}
