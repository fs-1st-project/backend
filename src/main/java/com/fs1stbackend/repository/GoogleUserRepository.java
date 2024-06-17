package com.fs1stbackend.repository;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class GoogleUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(String email, String uid) {
        // 이메일 존재 여부 확인 쿼리
        String checkEmailSql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(checkEmailSql, new Object[]{email}, Integer.class);

        // 이메일이 존재하지 않을 경우에만 삽입
        if (count == null || count == 0) {
            String insertSql = "INSERT INTO users (email, password) VALUES (?, ?)";
            jdbcTemplate.update(insertSql, email, uid);
        } else {
            // 이미 존재하는 이메일 처리 (필요 시)
            System.out.println("Email already exists: " + email);
        }
    }

    public Long findUserIdByUid(String uid) {
        String sql = "SELECT id FROM users WHERE password = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{uid}, Long.class);
    }

    public GoogleUserProfileDTO getUserProfileById(Long userId) {
        String sql = "SELECT u.email, u.password, up.profile_picture, up.profile_background_picture, up.full_name, " +
                "up.introduction, up.bio, up.education, up.location, up.certification " +
                "FROM users u JOIN user_profiles up ON u.id = up.user_id WHERE u.id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new RowMapper<GoogleUserProfileDTO>() {
            @Override
            public GoogleUserProfileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new GoogleUserProfileDTO(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBytes("profile_picture"),
                        rs.getBytes("profile_background_picture"),
                        rs.getString("full_name"),
                        rs.getString("introduction"),
                        rs.getString("bio"),
                        rs.getString("education"),
                        rs.getString("location"),
                        rs.getString("certification")
                );
            }
        });
    }

    public void updateUserProfile(Long userId, GoogleUserProfileUpdateDTO profileUpdateDTO) {
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

        jdbcTemplate.update(updateSql,
                profileUpdateDTO.getFullName(),
                profileUpdateDTO.getIntroduction(),
                profileUpdateDTO.getBio(),
                profileUpdateDTO.getEducation(),
                profileUpdateDTO.getLocation(),
                profileUpdateDTO.getCertification(),
                profileUpdateDTO.getProfilePicture(),
                profileUpdateDTO.getProfileBackgroundPicture(),
                userId);
    }

}
