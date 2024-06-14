package com.fs1stbackend.service;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.repository.GoogleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleUserHomeService {

    @Autowired
    private GoogleUserRepository googleUserRepository;

    public void saveUser(String email, String password) {
        googleUserRepository.save(email, password);
    }


    public GoogleUserProfileDTO getUserProfile(Long userId) {
        return googleUserRepository.getUserProfileById(userId);
    }
}
