package com.fs1stbackend.web;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
import com.fs1stbackend.service.GoogleUserHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public GoogleUserProfileDTO getUserProfile(@PathVariable String uid) {
        System.out.println("구글유저데이터 조회 컨트롤러에 닿았습니다");
        return googleUserService.getUserProfileByUid(uid);
    }

    @PutMapping("/update/{uid}/profile")
    public ResponseEntity<String> updateUserProfile(
            @PathVariable String uid,
            @RequestBody GoogleUserProfileUpdateDTO profileUpdateDTO) {

        String updateResult = googleUserService.updateUserProfile(uid, profileUpdateDTO);

        if (updateResult.equals("해당 프로필 수정에 실패하였습니다")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("프로필 업데이트 실패");
        } else {
            return ResponseEntity.ok(updateResult);
        }
    }






}
