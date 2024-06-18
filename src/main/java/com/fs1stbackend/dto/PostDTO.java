package com.fs1stbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String content;
    private String image;
    private Date createdAt;
    private Long userId;

    public PostDTO() {
    }

    public PostDTO(Long id, String content, String image, Date createdAt, Long userId) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.userId = userId;
    }
}
