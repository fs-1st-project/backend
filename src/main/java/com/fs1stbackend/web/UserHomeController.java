package com.fs1stbackend.web;

import com.fs1stbackend.dto.UserDTO;
import com.fs1stbackend.model.User;
import com.fs1stbackend.service.UserHomeService;
import com.fs1stbackend.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/home")
public class UserHomeController {

    @Autowired
    private UserHomeService userHomeService;

    @GetMapping("/user")
    public UserDTO getUserAtHome(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println("클라이언트로부터 토큰을 받았습니다");
        UserDTO userDTO = userHomeService.getUserAtHome(authorizationHeader);

        System.out.println("userDTO" + userDTO);

        if (userDTO != null) {
            return userDTO;
        } else {
            throw new UserNotFoundException("해당 토큰에 대한 유저 정보를 찾지 못하였습니다");
        }
    }
}
