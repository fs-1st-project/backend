package com.fs1stbackend.repository;

import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
import com.fs1stbackend.dto.UserAndUserProfileUpdateDTO;
import com.fs1stbackend.model.User;
import com.fs1stbackend.model.UserAndUserProfile;
import com.fs1stbackend.model.UserProfile;
import com.fs1stbackend.model.UserUserProfile;
import com.fs1stbackend.service.mapper.UserAndUserProfileRowMapper;
import com.fs1stbackend.service.mapper.UserProfileRowMapper;
import com.fs1stbackend.service.mapper.UserRowMapper;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserHomeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserUserProfile findUserProfileAtHome(String userEmail) {
        String sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
        User existingUser = jdbcTemplate.queryForObject(sql, new Object[]{userEmail}, new UserRowMapper());

        if (existingUser != null) {
            Long userId = existingUser.getId();
            String checkProfileSql = "SELECT COUNT(*) FROM user_profiles WHERE user_id = ?";
            int userProfileCount = jdbcTemplate.queryForObject(checkProfileSql, Integer.class, userId);

            UserProfile userProfile;

            if (userProfileCount == 0) {
                String insertSql = "INSERT INTO user_profiles (user_id, full_name) VALUES (?, ?)";
                jdbcTemplate.update(insertSql, userId, userEmail); // 유저 이메일을 이름으로 사용

                String selectSql = "SELECT * FROM user_profiles WHERE user_id = ? LIMIT 1";
                userProfile = jdbcTemplate.queryForObject(selectSql, new Object[]{userId}, new UserProfileRowMapper());
            } else {
                String selectSql = "SELECT * FROM user_profiles WHERE user_id = ? LIMIT 1";
                userProfile = jdbcTemplate.queryForObject(selectSql, new Object[]{userId}, new UserProfileRowMapper());
            }

            UserUserProfile userUserProfile = new UserUserProfile(existingUser, userProfile);
            return userUserProfile;
        }
        return null;
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
        // SQL 쿼리문
        StringBuilder updateSqlBuilder = new StringBuilder("UPDATE user_profiles up ");
        updateSqlBuilder.append("LEFT JOIN users u ON u.id = up.user_id SET ");

        // 프로필 사진 처리
        byte[] profilePicture = null;
        if (!StringUtils.isEmpty(profileUpdateDTO.getProfilePicture())) {
            String base64Url = profileUpdateDTO.getProfilePicture();
            String pureBase64Url = base64Url.substring(base64Url.indexOf(",") + 1);
            profilePicture = Base64.getDecoder().decode(pureBase64Url);
            updateSqlBuilder.append("up.profile_picture = ?, ");
        }

        // 프로필 배경 사진 처리
        byte[] profileBackgroundPicture = null;
        if (!StringUtils.isEmpty(profileUpdateDTO.getProfileBackgroundPicture())) {
            String base64Url = profileUpdateDTO.getProfileBackgroundPicture();
            String pureBase64Url = base64Url.substring(base64Url.indexOf(",") + 1);
            profileBackgroundPicture = Base64.getDecoder().decode(pureBase64Url);
            updateSqlBuilder.append("up.profile_background_picture = ?, ");
        }

        // 나머지 업데이트 필드 처리
        boolean hasFieldsToUpdate = false;
        if (!StringUtils.isEmpty(profileUpdateDTO.getFullName())) {
            updateSqlBuilder.append("up.full_name = ?, ");
            hasFieldsToUpdate = true;
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getIntroduction())) {
            updateSqlBuilder.append("up.introduction = ?, ");
            hasFieldsToUpdate = true;
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getBio())) {
            updateSqlBuilder.append("up.bio = ?, ");
            hasFieldsToUpdate = true;
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getEducation())) {
            updateSqlBuilder.append("up.education = ?, ");
            hasFieldsToUpdate = true;
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getLocation())) {
            updateSqlBuilder.append("up.location = ?, ");
            hasFieldsToUpdate = true;
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getCertification())) {
            updateSqlBuilder.append("up.certification = ?, ");
            hasFieldsToUpdate = true;
        }

        if (!hasFieldsToUpdate) {
            return "no fields to update";
        }

        // 마지막 콤마 제거
        updateSqlBuilder.delete(updateSqlBuilder.length() - 2, updateSqlBuilder.length());

        // WHERE 절 추가
        updateSqlBuilder.append(" WHERE u.id = ?");

        // Update 쿼리 실행
        String updateSql = updateSqlBuilder.toString();
        Object[] params = collectUpdateParams(profileUpdateDTO, profilePicture, profileBackgroundPicture, userId);
        int rowsAffected = jdbcTemplate.update(updateSql, params);

        // 업데이트 결과에 따른 반환 메시지
        return rowsAffected > 0 ? "update success" : "update failed";
    }

    private Object[] collectUpdateParams(UserAndUserProfileUpdateDTO profileUpdateDTO, byte[] profilePicture, byte[] profileBackgroundPicture, Long userId) {
        // 파라미터 수집
        List<Object> params = new ArrayList<>();
        if (profilePicture != null) {
            params.add(profilePicture);
        }
        if (profileBackgroundPicture != null) {
            params.add(profileBackgroundPicture);
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getFullName())) {
            params.add(profileUpdateDTO.getFullName());
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getIntroduction())) {
            params.add(profileUpdateDTO.getIntroduction());
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getBio())) {
            params.add(profileUpdateDTO.getBio());
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getEducation())) {
            params.add(profileUpdateDTO.getEducation());
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getLocation())) {
            params.add(profileUpdateDTO.getLocation());
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getCertification())) {
            params.add(profileUpdateDTO.getCertification());
        }
        params.add(userId);
        return params.toArray();
    }

}



