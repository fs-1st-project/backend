package com.fs1stbackend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntirePost {
    private Post post;
    private User user;
    private UserProfile userProfile;
}
