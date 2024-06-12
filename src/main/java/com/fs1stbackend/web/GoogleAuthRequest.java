package com.fs1stbackend.web;


// 추가: GoogleAuthRequest 클래스 정의 = dto
public class GoogleAuthRequest {
    private String idToken;

    public GoogleAuthRequest() {
        // 기본 생성자
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}