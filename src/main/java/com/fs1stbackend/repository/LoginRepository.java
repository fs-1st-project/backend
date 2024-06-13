package com.fs1stbackend.repository;

import com.fs1stbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            User existingUser = jdbcTemplate.queryForObject(sql, new Object[]{email, password}, new UserRowMapper());
            return Optional.ofNullable(existingUser);
        }


        public class UserRowMapper implements RowMapper<User> {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return User.builder()
                        .id(rs.getLong("id"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build();
            }
        }
}

