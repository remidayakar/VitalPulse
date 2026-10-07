package com.example.devops_project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/mentors")
    @ResponseBody
    public List<User> getMentors() {
        return userRepository.findByRole("MENTOR");
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody Map<String, Object> payload) {
        String username = (String) payload.get("username");
        String password = (String) payload.get("password");
        String role = (String) payload.getOrDefault("role", "CLIENT");

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role.toUpperCase());

        if ("CLIENT".equalsIgnoreCase(role) && payload.get("mentorId") != null) {
            Long mentorId = Long.valueOf(payload.get("mentorId").toString());
            userRepository.findById(mentorId).ifPresent(user::setMentor);
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}