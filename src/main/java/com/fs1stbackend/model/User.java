package com.fs1stbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String password;

    public User() {
    }

}
