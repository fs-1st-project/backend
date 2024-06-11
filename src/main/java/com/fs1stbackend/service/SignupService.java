package com.fs1stbackend.service;

import com.fs1stbackend.model.User;
import com.fs1stbackend.repository.SignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    @Autowired
    private SignupRepository signupRepository;

    public User registerUser(User user) {
        return signupRepository.save(user);
    }

    public User getUserById(Long id){
        return signupRepository.findById(id);
    }
}
