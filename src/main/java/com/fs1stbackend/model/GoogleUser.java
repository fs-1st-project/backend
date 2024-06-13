package com.fs1stbackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
public class GoogleUser {
    @Id

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
