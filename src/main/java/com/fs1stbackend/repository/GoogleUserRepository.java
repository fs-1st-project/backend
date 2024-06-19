package com.fs1stbackend.repository;

import com.fs1stbackend.dto.GoogleUserProfileDTO;
import com.fs1stbackend.dto.GoogleUserProfileUpdateDTO;
import com.fs1stbackend.dto.UserAndUserProfileDTO;
import io.micrometer.common.util.StringUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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
                        rs.getString("profile_picture"),
                        rs.getString("profile_background_picture"),
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


    public String updateUserProfile(Long userId, GoogleUserProfileUpdateDTO profileUpdateDTO) {
        // SQL 쿼리문
        StringBuilder updateSqlBuilder = new StringBuilder("UPDATE user_profiles up ");
        updateSqlBuilder.append("JOIN users u ON u.id = up.user_id SET ");

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
        if (!StringUtils.isEmpty(profileUpdateDTO.getFullName())) {
            updateSqlBuilder.append("up.full_name = ?, ");
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getIntroduction())) {
            updateSqlBuilder.append("up.introduction = ?, ");
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getBio())) {
            updateSqlBuilder.append("up.bio = ?, ");
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getEducation())) {
            updateSqlBuilder.append("up.education = ?, ");
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getLocation())) {
            updateSqlBuilder.append("up.location = ?, ");
        }
        if (!StringUtils.isEmpty(profileUpdateDTO.getCertification())) {
            updateSqlBuilder.append("up.certification = ?, ");
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

    private Object[] collectUpdateParams(GoogleUserProfileUpdateDTO profileUpdateDTO, byte[] profilePicture, byte[] profileBackgroundPicture, Long userId) {
        // 파라미터 수집
        // 여기서 profilePicture와 profileBackgroundPicture를 Object[]에 추가
        List<Object> params = new ArrayList<>();
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
        if (profilePicture != null) {
            params.add(profilePicture);
        }
        if (profileBackgroundPicture != null) {
            params.add(profileBackgroundPicture);
        }
        params.add(userId);
        return params.toArray();
    }
}
