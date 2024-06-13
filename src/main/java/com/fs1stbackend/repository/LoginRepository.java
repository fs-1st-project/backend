package com.fs1stbackend.repository;

import com.fs1stbackend.model.User;
import com.fs1stbackend.service.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? LIMIT 1";
        try{
            User existingUser = jdbcTemplate.queryForObject(sql, new Object[]{email, password}, new UserRowMapper());
            return Optional.ofNullable(existingUser);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        }

}

