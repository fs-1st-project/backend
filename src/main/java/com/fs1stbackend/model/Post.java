package com.fs1stbackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    private Long id;
    private String content;
    private byte[] image;
    private Timestamp createdAt;
    private Long userId;
}
