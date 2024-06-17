package com.fs1stbackend.service;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
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


    public GoogleUserProfileDTO getUserProfileByUid(String uid) {
        Long userId = googleUserRepository.findUserIdByUid(uid);
        return googleUserRepository.getUserProfileById(userId);
    }

    public GoogleUserProfileDTO updateUserProfile(String uid, GoogleUserProfileUpdateDTO profileUpdateDTO) {
        Long userId = googleUserRepository.findUserIdByUid(uid);
        googleUserRepository.updateUserProfile(userId, profileUpdateDTO);
        GoogleUserProfileDTO updatedProfile = googleUserRepository.getUserProfileById(userId);
        System.out.println("업데이트된 프로필 정보: " + updatedProfile);
        return updatedProfile;
    }


}
