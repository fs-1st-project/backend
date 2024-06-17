package com.fs1stbackend.repository;

import com.fs1stbackend.dto.PostDTO;
import com.fs1stbackend.model.EntirePost;
import com.fs1stbackend.model.User;
import com.fs1stbackend.service.exception.UserNotFoundException;
import com.fs1stbackend.service.mapper.EntirePostRowMapper;
import com.fs1stbackend.service.mapper.UserRowMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Repository
public class PostRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public String save(String email, PostDTO postDTO) {
        String userSql = "SELECT * FROM users WHERE email = ? LIMIT 1";
        try {
            User user = jdbcTemplate.queryForObject(userSql, new Object[]{email}, new UserRowMapper());
            if (user != null) {
                if (postDTO.getImage().isEmpty()) {
                    String createPostWithoutImgSql = "INSERT INTO posts (content, created_at) VALUES (?, ?)";
                    jdbcTemplate.update(createPostWithoutImgSql, postDTO.getContent(), postDTO.getCreated_at());
                    return "컨텐츠, 시간 데이터에 삽입 되었습니다.";
                } else {
                    String createPostSql = "INSERT INTO posts (content, image, created_at) VALUES (?, ?, ?)";
                    String base64Url = postDTO.getImage();
                    String pureBase64Url = base64Url.substring(base64Url.indexOf(",") + 1);
                    byte[] imageBytes = Base64.getDecoder().decode(pureBase64Url);

                    jdbcTemplate.update(createPostSql, postDTO.getContent(), imageBytes, postDTO.getCreated_at());

                }
                return "컨텐츠, 이미지, 시간 데이터에 삽입 되었습니다.";
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            System.out.println("포스트 저장 중 예외가 발생했습니다.");
        }
        return "로그인한 유저와 같은 유저만 게시글 작성이 가능합니다.";
    }

    public List<EntirePost> getAllPost() {
        String sql = "SELECT p.content, p.image, p.created_at, p.user_id, f.profile_picture, f.full_name, f.introduction FROM posts p " +
                "JOIN users u ON p.user_id = u.id " +
                "JOIN user_profiles f ON u.id = f.user_id";

        return jdbcTemplate.query(sql, new EntirePostRowMapper());
    }
}


