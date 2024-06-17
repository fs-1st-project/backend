package com.fs1stbackend.service.mapper;

import com.fs1stbackend.model.Post;
import com.fs1stbackend.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostRowMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Post.builder()
                .id(rs.getLong("id"))
                .content(rs.getString("content"))
                .image(rs.getBytes("image"))
                .createdAt(rs.getTimestamp("created_at"))
                .build();
    }
}
