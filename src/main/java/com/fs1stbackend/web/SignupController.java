package com.fs1stbackend.web;


import com.fs1stbackend.dto.SignupDTO;
import com.fs1stbackend.model.User;
import com.fs1stbackend.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @PostMapping("/register")
    public User registerUser(@RequestBody SignupDTO signupDTO) {
        User user = new User();
        user.setEmail(signupDTO.getEmail());
        user.setPassword(signupDTO.getPassword());
        return signupService.registerUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return signupService.getUserById(id);
    }
}
