package com.fs1stbackend.service.mapper;

import com.fs1stbackend.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntirePostRowMapper implements RowMapper<EntirePost> {
    @Override
    public EntirePost mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = Post.builder()
                .content(rs.getString("p.content"))
                .image(rs.getBytes("p.image"))
                .createdAt(rs.getTimestamp("p.created_at"))
                .build();

        User user = User.builder()
                .build();

        UserProfile userProfile = UserProfile.builder()
                .profilePicture(rs.getBytes("f.profile_picture"))
                .fullName(rs.getString("f.full_name"))
                .introduction(rs.getString("f.introduction"))
                .build();

        EntirePost entirePost = new EntirePost();
        entirePost.setPost(post);
        entirePost.setUser(user);
        entirePost.setUserProfile(userProfile);

        return entirePost;
    }
}
