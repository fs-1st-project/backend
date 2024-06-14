package com.fs1stbackend.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class GoogleUser {


    private String email;

    private String password;

    public GoogleUser() {
        // 기본 생성자
    }

    public GoogleUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //toString 생략
}
