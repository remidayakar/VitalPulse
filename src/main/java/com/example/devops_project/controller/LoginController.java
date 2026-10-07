package com.example.devops_project.controller;

import java.util.Optional;

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
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody User loginReq) {
        Optional<User> userOpt = userRepository.findByUsername(loginReq.getUsername());
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginReq.getPassword())) {
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}