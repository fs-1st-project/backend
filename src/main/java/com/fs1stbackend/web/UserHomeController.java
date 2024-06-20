package com.fs1stbackend.web;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
import com.fs1stbackend.dto.UserAndUserProfileDTO;
import com.fs1stbackend.dto.UserAndUserProfileUpdateDTO;
import com.fs1stbackend.service.UserHomeService;
import com.fs1stbackend.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class UserHomeController {

    @Autowired
    private UserHomeService userHomeService;

    @GetMapping("/user")
    public UserAndUserProfileDTO findUserProfileAtHome(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println("클라이언트로부터 토큰을 받았습니다");
        UserAndUserProfileDTO userAndUserProfileDTO = userHomeService.findUserProfileAtHome(authorizationHeader);

        if (userAndUserProfileDTO != null) {
            return userAndUserProfileDTO;
        } else {
            throw new UserNotFoundException("해당 토큰에 대한 유저 정보를 찾지 못하였습니다");
        }
    }

    @PutMapping("/update/{email}/profile")
    public ResponseEntity<String> updateUserProfile(@PathVariable String email, @RequestBody UserAndUserProfileUpdateDTO profileUpdateDTO) {
        String updateResult = userHomeService.updateUserProfile(email, profileUpdateDTO);
        // 요청이 들어왔음을 콘솔에 출력
        System.out.println("요청 들어옴");
        System.out.println("PUT 요청 수신: " + email + ", Request Body: " + profileUpdateDTO);
        if (updateResult.equals("해당 프로필 수정에 실패하였습니다")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("프로필 업데이트 실패");
        } else {
            return ResponseEntity.ok(updateResult);
        }
    }


}
