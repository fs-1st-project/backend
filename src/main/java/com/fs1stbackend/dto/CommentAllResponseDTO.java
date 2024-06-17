package com.fs1stbackend.dto;

import com.fs1stbackend.model.Comment;
import com.fs1stbackend.model.UserProfile;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentAllResponseDTO {
    private Long id;
    private String commentContent;
    private Date createdAt;

    private byte[] profilePicture;
    private String fullName;
    private String introduction;

    public CommentAllResponseDTO (Comment comment, UserProfile userProfile) {
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();

        this.profilePicture = userProfile.getProfilePicture();
        this.fullName = userProfile.getFullName();
        this.introduction = userProfile.getIntroduction();

    }

}
