package com.example.devops_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.devops_project.domain.User;
import com.example.devops_project.repository.UserRepository;

@Controller
@RequestMapping("/api/auth")
public class RegisterController {

    private final UserRepository userRepository;

    // Constructor Injection (No @Autowired needed on modern Spring)
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}