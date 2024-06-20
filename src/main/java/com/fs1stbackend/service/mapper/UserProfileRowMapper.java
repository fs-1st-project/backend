package com.fs1stbackend.service.mapper;

import com.fs1stbackend.model.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileRowMapper implements RowMapper<UserProfile> {
    @Override
    public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserProfile.builder()
                .id(rs.getLong("id"))
                .profilePicture(rs.getBytes("profile_picture"))
                .profileBackGroundPicture(rs.getBytes("profile_background_picture"))
                .fullName(rs.getString("full_name"))
                .introduction(rs.getString("introduction"))
                .bio(rs.getString("bio"))
                .education(rs.getString("education"))
                .location(rs.getString("location"))
                .certification(rs.getString("certification"))
                .userId(rs.getLong("user_id"))
                .build();
    }
}


