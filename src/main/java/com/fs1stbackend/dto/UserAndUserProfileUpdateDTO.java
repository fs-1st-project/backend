package com.fs1stbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAndUserProfileUpdateDTO {
    private String profilePicture;
    private String profileBackgroundPicture;
    private String fullName;
    private String introduction;
    private String bio;
    private String education;
    private String location;
    private String certification;
}
