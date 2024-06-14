package com.fs1stbackend.dto;

import com.fs1stbackend.model.User;
import com.fs1stbackend.model.UserAndUserProfile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAndUserProfileDTO {
    private String email;
    private byte[] profilePicture;
    private byte[] profileBackGroundPicture;
    private String fullName;
    private String introduction;
    private String bio;
    private String education;
    private String location;
    private String certification;

    public UserAndUserProfileDTO(UserAndUserProfile userAndUserProfile) {
        this.email = userAndUserProfile.getUser().getEmail();
        this.profilePicture = userAndUserProfile.getUserProfile().getProfilePicture();
        this.profileBackGroundPicture = userAndUserProfile.getUserProfile().getProfileBackGroundPicture();
        this.fullName = userAndUserProfile.getUserProfile().getFullName();
        this.introduction = userAndUserProfile.getUserProfile().getIntroduction();
        this.bio = userAndUserProfile.getUserProfile().getBio();
        this.education = userAndUserProfile.getUserProfile().getEducation();
        this.location = userAndUserProfile.getUserProfile().getLocation();
        this.certification = userAndUserProfile.getUserProfile().getCertification();
    }
}
