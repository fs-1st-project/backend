package com.fs1stbackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

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
}
