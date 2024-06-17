package com.fs1stbackend.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    private Long id;
    private String commentContent;
    private Timestamp createdAt;
    private Long userId;
    private Long postId;
}
