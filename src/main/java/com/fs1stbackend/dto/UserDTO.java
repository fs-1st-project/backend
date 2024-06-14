package com.fs1stbackend.dto;

import com.fs1stbackend.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
