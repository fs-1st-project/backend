package com.fs1stbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleUserProfileUpdateDTO {
    private String profilePicture; // Base64 encoded string
    private String profileBackgroundPicture; // Base64 encoded string
    private String fullName;
    private String introduction;
    private String bio;
    private String education;
    private String location;
    private String certification;
}
