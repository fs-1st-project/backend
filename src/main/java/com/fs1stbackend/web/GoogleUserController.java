package com.fs1stbackend.web;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.service.GoogleUserHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class GoogleUserController {

    @Autowired
    private GoogleUserHomeService googleUserService;

//    @GetMapping("/{userId}/profile")
//    public GoogleUserProfileDTO getUserProfile(@PathVariable Long userId) {
//
//        return googleUserService.getUserProfile(userId);
//    }

    @GetMapping("/{uid}/profile")
    public GoogleUserProfileDTO getUserProfile(@PathVariable String uid, @RequestHeader("Authorization") String token) {
        return googleUserService.getUserProfileByUid(uid);
    }

}
