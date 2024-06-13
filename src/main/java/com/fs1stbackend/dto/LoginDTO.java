package com.fs1stbackend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;
}
