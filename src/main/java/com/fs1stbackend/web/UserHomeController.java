package com.fs1stbackend.web;

import com.fs1stbackend.service.UserHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class UserHomeController {

    @Autowired
    private UserHomeService userHomeService;

    @GetMapping("/user")
    public void getUserAtHome(@RequestHeader("Authorization") String authorizationHeader) {
        userHomeService.getUserAtHome(authorizationHeader);
    }




}
