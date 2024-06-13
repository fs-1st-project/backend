package com.fs1stbackend.repository;

import com.fs1stbackend.model.User;
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

//    private static class UserRowMapper implements RowMapper<User> {
//        @Override
//        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//            User user = new User();
//            user.setId(rs.getLong("id"));
//            user.setEmail(rs.getString("email"));
//            user.setPassword(rs.getString("password"));
//            return user;
//        }
//    }
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
