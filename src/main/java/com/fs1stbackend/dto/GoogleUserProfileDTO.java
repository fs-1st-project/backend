package com.fs1stbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserProfileDTO {
    private String email;
    private String password;
    private byte[] profilePicture;
    private byte[] profileBackgroundPicture;
    private String fullName;
    private String introduction;
    private String bio;
    private String education;
    private String location;
    private String certification;
}
