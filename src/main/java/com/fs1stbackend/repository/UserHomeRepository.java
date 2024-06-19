package com.fs1stbackend.repository;

import com.fs1stbackend.dto.UserAndUserProfileUpdateDTO;
import com.fs1stbackend.model.User;
import com.fs1stbackend.model.UserAndUserProfile;
import com.fs1stbackend.service.mapper.UserAndUserProfileRowMapper;
import com.fs1stbackend.service.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

@Repository
public class UserHomeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserAndUserProfile findUserProfileAtHome(String userEmail) {
        String sql = "SELECT * " +
                "FROM users u " +
                "JOIN user_profiles p ON u.id = p.user_id " +
                "WHERE u.email = ? " +
                "LIMIT 1";

        UserAndUserProfile user =  jdbcTemplate.queryForObject(sql, new Object[]{userEmail}, new UserAndUserProfileRowMapper());
        return user;

        }

    public Long findUserIdByEmail(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Long.class, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String updateUserProfile(Long userId, UserAndUserProfileUpdateDTO profileUpdateDTO) {
        String updateSql = "UPDATE user_profiles up " +
                "JOIN users u ON u.id = up.user_id " +
                "SET up.full_name = ?, " +
                "    up.introduction = ?, " +
                "    up.bio = ?, " +
                "    up.education = ?, " +
                "    up.location = ?, " +
                "    up.certification = ?, " +
                "    up.profile_picture = ?, " +
                "    up.profile_background_picture = ? " +
                "WHERE u.id = ?";

        byte[] profilePicture = null;
        if (profileUpdateDTO.getProfilePicture() != null) {
            String base64Url = profileUpdateDTO.getProfilePicture();
            String pureBase64Url = base64Url.substring(base64Url.indexOf(",") + 1);
            profilePicture = Base64.getDecoder().decode(pureBase64Url);
        }

        byte[] profileBackgroundPicture = null;
        if (profileUpdateDTO.getProfileBackgroundPicture() != null) {
            String base64Url = profileUpdateDTO.getProfileBackgroundPicture();
            String pureBase64Url = base64Url.substring(base64Url.indexOf(",") + 1);
            profileBackgroundPicture = Base64.getDecoder().decode(pureBase64Url);
        }

        int rowsAffected = jdbcTemplate.update(updateSql,
                profileUpdateDTO.getFullName(),
                profileUpdateDTO.getIntroduction(),
                profileUpdateDTO.getBio(),
                profileUpdateDTO.getEducation(),
                profileUpdateDTO.getLocation(),
                profileUpdateDTO.getCertification(),
                profilePicture,
                profileBackgroundPicture,
                userId);
        System.out.println(rowsAffected);
        return rowsAffected > 0 ? "update success" : "update failed";
    }
    }



