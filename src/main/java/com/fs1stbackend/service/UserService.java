package com.fs1stbackend.service;

import com.fs1stbackend.model.GoogleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(GoogleUser user) {
        userRepository.save(user);
    }

    public GoogleUser getUserById(String uid) {
        return userRepository.findById(uid).orElse(null);
    }
}
