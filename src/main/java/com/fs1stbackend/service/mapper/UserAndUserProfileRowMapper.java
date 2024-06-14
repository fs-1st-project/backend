package com.fs1stbackend.service.mapper;

import com.fs1stbackend.model.User;
import com.fs1stbackend.model.UserAndUserProfile;
import com.fs1stbackend.model.UserProfile;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAndUserProfileRowMapper implements RowMapper<UserAndUserProfile> {

    @Override
    public UserAndUserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .build();

        UserProfile userProfile = UserProfile.builder()
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

        UserAndUserProfile userAndUserProfile = new UserAndUserProfile();
        userAndUserProfile.setUser(user);
        userAndUserProfile.setUserProfile(userProfile);

        return userAndUserProfile;
    }
}
