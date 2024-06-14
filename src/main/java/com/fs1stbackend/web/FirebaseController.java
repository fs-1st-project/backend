package com.fs1stbackend.web;

import com.fs1stbackend.dto.GoogleAuthRequestDTO;
import com.fs1stbackend.dto.UserInfoDTO;
import com.fs1stbackend.service.FirebaseService;
import com.fs1stbackend.service.FirebaseAuthService;
import com.fs1stbackend.service.GoogleUserHomeService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/firebase")
@Tag(name = "Firebase Controller", description = "Endpoints for Firebase operations")
public class FirebaseController {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Autowired
    private GoogleUserHomeService googleUserHomeService;

    @PostMapping("/auth/google")
    @Operation(summary = "Authenticate with Google", description = "Authenticate user with Google OAuth2 token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated with Google"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> authenticateWithGoogle(@RequestBody GoogleAuthRequestDTO request) {
        try {
            String idToken = request.getIdToken();
            FirebaseToken decodedToken = firebaseAuthService.verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            UserRecord userRecord;
            try {
                userRecord = firebaseAuthService.getUser(uid);
            } catch (FirebaseAuthException e) {
                userRecord = firebaseAuthService.createUser(uid, decodedToken.getEmail(), decodedToken.getName());
            }

            String customToken = firebaseAuthService.createCustomToken(uid);


            // 구글 로그인 성공 시 데이터베이스에 저장
            googleUserHomeService.saveUser(email, uid);

            //커스텀 토큰 출력해보기
            System.out.println("Custom Token: " + customToken);
            //userRecord의 정보를 로그로 출력
            System.out.println("User Record: " + userRecord.toString());
            System.out.println("출력테스트출력테스트");

            // 사용자 정보를 ResponseEntity로 반환
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("uid", userRecord.getUid());
            userInfo.put("email", userRecord.getEmail());
            userInfo.put("displayName", userRecord.getDisplayName());
            userInfo.put("photoUrl", userRecord.getPhotoUrl());

            // customToken과 userInfo를 함께 반환
            Map<String, Object> response = new HashMap<>();
            response.put("customToken", customToken);
            response.put("userInfo", userInfo);

            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // 사용자 정보를 GET 요청으로 반환하는 엔드포인트 추가
    @GetMapping("/auth/user/{uid}")
    public ResponseEntity<Object> getUserInfo(@PathVariable String uid) {
        try {
            UserRecord userRecord = firebaseAuthService.getUser(uid);

            //사용자 정보 가져오기: 유저 uid, 이메일, displayname, 사진 url
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUid(userRecord.getUid());
            userInfoDTO.setEmail(userRecord.getEmail());
            userInfoDTO.setDisplayName(userRecord.getDisplayName());
            userInfoDTO.setPhotoUrl(userRecord.getPhotoUrl());

            return ResponseEntity.ok(userInfoDTO);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        }
    }
}




