package com.fs1stbackend.dto;

import com.fs1stbackend.model.User;
import com.fs1stbackend.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenAndUserDataDTO {
    private String token;

    private Long userId;
    private String email;

    private byte[] profilePicture;
    private byte[] profileBackGroundPicture;
    private String fullName;
    private String introduction;
    private String bio;
    private String education;
    private String location;
    private String certification;

    public TokenAndUserDataDTO(String token, User user, UserProfile userProfile){
        this.token = token;
        this.userId = user.getId();
        this.email = user.getEmail();

        if(userProfile != null) {
            this.profilePicture = userProfile.getProfilePicture();
            this.profileBackGroundPicture = userProfile.getProfileBackGroundPicture();
            this.fullName = userProfile.getFullName();
            this.introduction = userProfile.getIntroduction();
            this.bio = userProfile.getBio();
            this.education = userProfile.getEducation();
            this.location = userProfile.getLocation();
            this.certification = userProfile.getCertification();
        } else {
            this.profilePicture = null;
            this.profileBackGroundPicture = null;
            this.fullName = null;
            this.introduction = null;
            this.bio = null;
            this.education = null;
            this.location = null;
            this.certification = null;
        }
    };
}
