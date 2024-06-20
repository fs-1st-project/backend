package com.fs1stbackend.service;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
import com.fs1stbackend.repository.GoogleUserRepository;
import com.fs1stbackend.service.exception.NoContentException;
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
        googleUserRepository.createUserProfileIfNeeded(userId.toString()); // userId를 String으로 변환하여 전달
        return googleUserRepository.getUserProfileById(userId);
    }


    public String updateUserProfile(String uid, GoogleUserProfileUpdateDTO profileUpdateDTO) {
        String username = profileUpdateDTO.getFullName();
        String introduction = profileUpdateDTO.getIntroduction();
        String bio = profileUpdateDTO.getBio();
        String education = profileUpdateDTO.getEducation();
        String location = profileUpdateDTO.getLocation();
        String certification = profileUpdateDTO.getCertification();
        String profilePicture = profileUpdateDTO.getProfilePicture();
        String profileBackgroundPicture = profileUpdateDTO.getProfileBackgroundPicture();

        String isProfileUpdateSuccess = "";
        //GoogleUserProfileDTO updatedProfile = new GoogleUserProfileDTO();

        try {
            //하나라도 값이 들어있으면 삽입
            if (!username.isEmpty() || !introduction.isEmpty() || !bio.isEmpty() ||
                    !education.isEmpty() || !location.isEmpty() || !certification.isEmpty() ||
                    !profilePicture.isEmpty() || !profileBackgroundPicture.isEmpty()) {
                Long userId = googleUserRepository.findUserIdByUid(uid);
                isProfileUpdateSuccess = googleUserRepository.updateUserProfile(userId, profileUpdateDTO);
            } else {
                isProfileUpdateSuccess = "해당 프로필 수정에 실패하였습니다";
                throw new Exception("해당 프로필 수정에 실패하였습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("게시글 업데이트 서비스 로직 중 예외 발생");
        }
        return isProfileUpdateSuccess;
    }
}
