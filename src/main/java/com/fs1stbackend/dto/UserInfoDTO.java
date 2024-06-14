package com.fs1stbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private String uid;
    private String email;
    private String displayName;
    private String photoUrl;

    public UserInfoDTO() {
    }


}
