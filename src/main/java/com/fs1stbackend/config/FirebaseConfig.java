package com.fs1stbackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

/*Firebase Admin SDK 초기화*/

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        // 서비스 계정의 JSON 키 파일 경로
        String serviceAccountKeyPath = "src/main/java/com/fs1stbackend/.env";

        // Firebase 앱 초기화를 위한 옵션 설정
        FileInputStream serviceAccount = new FileInputStream(serviceAccountKeyPath);

        // Initialize the app with a service account, granting admin privileges
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://linkedin-e01a1-default-rtdb.firebaseio.com")
                .build();

        // Firebase 앱 초기화 및 반환
        FirebaseApp.initializeApp(options);
        System.out.println("Firebase Admin SDK 초기화 완료");
        return FirebaseApp.getInstance();
    }
}
