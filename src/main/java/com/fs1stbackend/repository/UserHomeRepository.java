package com.fs1stbackend.repository;

import com.fs1stbackend.model.User;
import com.fs1stbackend.model.UserAndUserProfile;
import com.fs1stbackend.service.mapper.UserAndUserProfileRowMapper;
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
public class UserHomeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserAndUserProfile findUserProfileAtHome(String userEmail) {
        String sql = "SELECT * " +
                "FROM users u " +
                "JOIN user_profiles p ON u.id = p.user_id " +
                "WHERE u.email = ? " +
                "LIMIT 1";

        UserAndUserProfile user =  jdbcTemplate.queryForObject(sql, new Object[]{userEmail}, new UserAndUserProfileRowMapper());
        return user;

        }
    }

