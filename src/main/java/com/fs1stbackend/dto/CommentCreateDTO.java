package com.fs1stbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentCreateDTO {
    private String commentContent;
    private Date createdAt;
    private Long userId;
    private Long postId;
}
