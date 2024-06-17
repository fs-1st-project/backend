package com.fs1stbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleUserProfileUpdateDTO {
    private byte[] profilePicture;
    private byte[] profileBackgroundPicture;
    private String fullName;
    private String introduction;
    private String bio;
    private String education;
    private String location;
    private String certification;
}
