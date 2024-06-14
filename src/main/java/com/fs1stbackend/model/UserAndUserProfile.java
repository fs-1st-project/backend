package com.fs1stbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAndUserProfile {
    private User user;
    private UserProfile userProfile;
}
