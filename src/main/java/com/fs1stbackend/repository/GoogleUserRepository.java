package com.fs1stbackend.repository;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
import com.fs1stbackend.dto.UserAndUserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

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
                // Get profile picture as Base64 string
                String profilePictureBase64 = null;
                if (rs.getBlob("profile_picture") != null) {
                    profilePictureBase64 = convertBlobToBase64(rs.getBlob("profile_picture"));
                }

                // Get profile background picture as Base64 string
                String profileBackgroundPictureBase64 = null;
                if (rs.getBlob("profile_background_picture") != null) {
                    profileBackgroundPictureBase64 = convertBlobToBase64(rs.getBlob("profile_background_picture"));
                }

                return new GoogleUserProfileDTO(
                        rs.getString("email"),
                        rs.getString("password"),
                        profilePictureBase64,
                        profileBackgroundPictureBase64,
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

    // Helper method to convert Blob to Base64 string
    private String convertBlobToBase64(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String updateUserProfile(Long userId, GoogleUserProfileUpdateDTO profileUpdateDTO) {
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
                profilePicture, // Use profilePicture here instead of base64Url
                profileBackgroundPicture,
                userId);

        System.out.println(rowsAffected);
        return rowsAffected > 0 ? "update success" : "update failed";
    }




}
