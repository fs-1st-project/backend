package com.fs1stbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserUserProfile {
    private User user;
    private UserProfile userProfile;
}
