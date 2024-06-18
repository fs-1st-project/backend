package com.fs1stbackend.web;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
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

    //@RequestHeader("Authorization") String token 뒤에 이거 추가해주기
    @PutMapping("/{uid}/profile")
    public GoogleUserProfileDTO updateUserProfile(@PathVariable String uid, @RequestBody GoogleUserProfileUpdateDTO profileUpdateDTO) {
        return googleUserService.updateUserProfile(uid, profileUpdateDTO);
    }

}
