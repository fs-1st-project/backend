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
                .id(rs.getLong("u.id"))
                .email(rs.getString("u.email"))
                .password(rs.getString("u.password"))
                .build();

        UserProfile userProfile = UserProfile.builder()
                .id(rs.getLong("p.id"))
                .profilePicture(rs.getBytes("p.profile_picture"))
                .profileBackGroundPicture(rs.getBytes("p.profile_background_picture"))
                .fullName(rs.getString("p.full_name"))
                .introduction(rs.getString("p.introduction"))
                .bio(rs.getString("p.bio"))
                .education(rs.getString("p.education"))
                .location(rs.getString("p.location"))
                .certification(rs.getString("p.certification"))
                .userId(rs.getLong("p.user_id"))
                .build();

        UserAndUserProfile userAndUserProfile = new UserAndUserProfile();
        userAndUserProfile.setUser(user);
        userAndUserProfile.setUserProfile(userProfile);

        return userAndUserProfile;
    }
}
