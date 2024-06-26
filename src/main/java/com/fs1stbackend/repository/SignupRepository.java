package com.fs1stbackend.repository;

import com.fs1stbackend.model.User;
import com.fs1stbackend.service.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public  class SignupRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User save(User user) {
        String sql = "INSERT INTO users (email, password) VALUES (?,?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword());
        return user;
    }

    public User findById(Long id){
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
    }
}
