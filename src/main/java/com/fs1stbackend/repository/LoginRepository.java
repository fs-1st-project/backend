package com.fs1stbackend.repository;

import com.fs1stbackend.model.User;
import com.fs1stbackend.model.UserProfile;
import com.fs1stbackend.model.UserUserProfile;
import com.fs1stbackend.service.mapper.UserProfileRowMapper;
import com.fs1stbackend.service.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class LoginRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserUserProfile loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? LIMIT 1";
            User existingUser = jdbcTemplate.queryForObject(sql, new Object[]{email, password}, new UserRowMapper());

            //유저 프로필 확인
            if(existingUser != null) {
                Long userId = existingUser.getId();
                String checkProfileSql = "SELECT * FROM user_profiles WHERE user_id = ?";


                    List<UserProfile> userProfile = jdbcTemplate.query(checkProfileSql, new Object[]{userId}, new UserProfileRowMapper());
                    UserProfile existingUserProfile = userProfile.isEmpty() ? null : userProfile.get(0);

                        UserUserProfile userUserProfile = new UserUserProfile(existingUser, existingUserProfile);
                        return userUserProfile;

            }
            return null;
        }

}

