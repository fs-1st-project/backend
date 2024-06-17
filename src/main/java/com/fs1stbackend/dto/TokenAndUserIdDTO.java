package com.fs1stbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenAndUserIdDTO {
    private String token;
    private Long userId;
}
