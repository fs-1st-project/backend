package com.fs1stbackend.web;

import com.fs1stbackend.service.FirebaseService;
import com.fs1stbackend.service.FirebaseAuthService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/firebase")
@Tag(name = "Firebase Controller", description = "Endpoints for Firebase operations")
public class FirebaseController {

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @GetMapping("/data/{path}")
    @Operation(summary = "Get data from Firebase", description = "Get data from a specific path in Firebase database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> getData(@PathVariable String path) {
        try {
            // 비동기 방식으로 데이터를 가져옵니다.
            firebaseService.getData(path, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // 데이터를 가져왔을 때의 처리 로직입니다.
                    Object data = dataSnapshot.getValue();

                    // 응답을 직접 반환하는 대신 데이터를 필드에 저장하고,
                    // 비동기 처리가 완료된 후에 응답을 반환합니다.
                    // 여기서는 ResponseEntity를 필드로 선언하여 사용합니다.
                    responseData = ResponseEntity.ok(data);
                    System.out.println("Success!");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // 오류가 발생했을 때의 처리 로직입니다.
                    // 오류 메시지를 필드에 저장하고, 비동기 처리가 완료된 후에 응답을 반환합니다.
                    errorResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(databaseError.getMessage());
                }
            });

            // 비동기 처리를 기다리지 않고 바로 응답을 반환합니다.
            return ResponseEntity.ok("Request is being processed");
        } catch (Exception e) {
            // 동기적으로 처리되는 오류를 여기서 처리합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 클래스 내에 ResponseEntity 필드를 선언합니다.
    private ResponseEntity<Object> responseData;
    private ResponseEntity<Object> errorResponse;


    @PostMapping("/auth/google")
    @Operation(summary = "Authenticate with Google", description = "Authenticate user with Google OAuth2 token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated with Google"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> authenticateWithGoogle(@RequestBody GoogleAuthRequest request) {
        try {
            String idToken = request.getIdToken();
            FirebaseToken decodedToken = firebaseAuthService.verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            UserRecord userRecord;
            try {
                userRecord = firebaseAuthService.getUser(uid);
            } catch (FirebaseAuthException e) {
                userRecord = firebaseAuthService.createUser(uid, decodedToken.getEmail(), decodedToken.getName());
            }

            String customToken = firebaseAuthService.createCustomToken(uid);

            return ResponseEntity.ok(customToken);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid ID token: " + e.getMessage());
        }
    }

    // 추가: GoogleAuthRequest 클래스 정의 = dto
    class GoogleAuthRequest {
        private String idToken;

        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}



