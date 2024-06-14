package com.fs1stbackend.service;

import com.fs1stbackend.model.GoogleUser;
import com.fs1stbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(String email, String password) {
        userRepository.save(email, password);
    }


//    public GoogleUser getUserById(String uid) {
//        return userRepository.findById(uid).orElse(null);
//    }
}
