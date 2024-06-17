package com.fs1stbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntireComment {
    private Comment comment;
    private User user;
    private UserProfile userProfile;
}
