package com.fs1stbackend.dto;


import com.fs1stbackend.model.Post;
import com.fs1stbackend.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PostResponseDTO {
    private String content;
    private byte[] image;
    private Date createdAt;

    private byte[] profilePicture;
    private String fullName;
    private String introduction;

    public PostResponseDTO(Post post, UserProfile userProfile) {
        this.content = post.getContent();
        this.image = post.getImage();
        this.createdAt = post.getCreatedAt();
        this.profilePicture = userProfile.getProfilePicture();
        this.fullName = userProfile.getFullName();
        this.introduction = userProfile.getIntroduction();
    }
}

