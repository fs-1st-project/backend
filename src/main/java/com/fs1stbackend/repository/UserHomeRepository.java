package com.fs1stbackend.repository;

import com.fs1stbackend.model.User;
import com.fs1stbackend.service.mapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserHomeRepository {

    private JdbcTemplate jdbcTemplate;

    public User getUserAtHome(String userEmail) {
        String sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
        try{
            return jdbcTemplate.queryForObject(sql, new Object[]{userEmail}, new UserRowMapper());

        } catch (Exception e) {
            return
        }
    }

}
