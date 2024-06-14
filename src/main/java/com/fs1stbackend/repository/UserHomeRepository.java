package com.fs1stbackend.repository;

import com.fs1stbackend.model.User;
import com.fs1stbackend.service.mapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserHomeRepository {

    private JdbcTemplate jdbcTemplate;

    public Optional<User> getUserAtHome(String userEmail) {
        String sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
        try{
            User user = jdbcTemplate.queryForObject(sql, new Object[]{userEmail}, new UserRowMapper());
            return Optional.ofNullable(user);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
