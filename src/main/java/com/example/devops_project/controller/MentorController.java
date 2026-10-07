package com.example.devops_project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.devops_project.domain.HealthLog;
import com.example.devops_project.domain.User;
import com.example.devops_project.repository.HealthLogRepository;
import com.example.devops_project.repository.UserRepository;

@Controller
@RequestMapping("/api/mentor")
public class MentorController {

    private final UserRepository userRepository;
    private final HealthLogRepository healthLogRepository;

    public MentorController(UserRepository userRepository, HealthLogRepository healthLogRepository) {
        this.userRepository = userRepository;
        this.healthLogRepository = healthLogRepository;
    }

    // Get all clients assigned to a specific mentor
    @GetMapping("/clients/{mentorId}")
    @ResponseBody
    public ResponseEntity<?> getAssignedClients(@PathVariable Long mentorId) {
        User mentor = userRepository.findById(mentorId).orElse(null);
        if (mentor == null || !"MENTOR".equalsIgnoreCase(mentor.getRole())) {
            return ResponseEntity.badRequest().body("Invalid Mentor ID");
        }
        List<User> clients = userRepository.findByMentor(mentor);
        return ResponseEntity.ok(clients);
    }

    // Get health logs for a specific client
    @GetMapping("/client-logs/{clientId}")
    @ResponseBody
    public List<HealthLog> getClientLogs(@PathVariable Long clientId) {
        User client = userRepository.findById(clientId).orElse(null);
        return healthLogRepository.findByUserOrderByDateDesc(client);
    }
}