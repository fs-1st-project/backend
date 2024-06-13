package com.fs1stbackend.dto;


// 추가: GoogleAuthRequest 클래스 정의 = dto
public class GoogleAuthRequestDTO {
    private String idToken;

    public GoogleAuthRequestDTO() {
        // 기본 생성자
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}