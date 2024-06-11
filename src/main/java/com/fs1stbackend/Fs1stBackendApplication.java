package com.fs1stbackend;

import com.fs1stbackend.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Fs1stBackendApplication implements CommandLineRunner {

    @Autowired
    private FirebaseService firebaseService;

    public static void main(String[] args) {
        SpringApplication.run(Fs1stBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        firebaseService.fetchDataFromFirebase();
    }
}
