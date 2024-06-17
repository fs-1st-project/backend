package com.fs1stbackend.service.mapper;

import com.fs1stbackend.model.Comment;
import com.fs1stbackend.model.EntireComment;
import com.fs1stbackend.model.User;
import com.fs1stbackend.model.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntireCommentRowMapper implements RowMapper<EntireComment> {
    @Override
    public EntireComment mapRow(ResultSet rs, int rowNum) throws SQLException{
        Comment comment = Comment.builder()
                        .commentContent(rs.getString("c.comment_content"))
                        .createdAt(rs.getTimestamp("c.created_at"))
                        .build();

        User user = User.builder()
                .build();

        UserProfile userProfile = UserProfile.builder()
                .profilePicture(rs.getBytes("f.profile_picture"))
                .fullName(rs.getString("f.full_name"))
                .introduction(rs.getString("f.introduction"))
                .build();

        EntireComment entireComment = new EntireComment();
        entireComment.setComment(comment);
        entireComment.setUser(user);
        entireComment.setUserProfile(userProfile);

        return entireComment;
    }
}
