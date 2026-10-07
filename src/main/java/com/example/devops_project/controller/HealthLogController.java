package com.example.devops_project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.devops_project.domain.HealthLog;
import com.example.devops_project.domain.User;
import com.example.devops_project.repository.HealthLogRepository;
import com.example.devops_project.repository.UserRepository;

@Controller
@RequestMapping("/api/health")
public class HealthLogController {

    private final HealthLogRepository healthLogRepository;
    private final UserRepository userRepository;

    public HealthLogController(HealthLogRepository healthLogRepository, UserRepository userRepository) {
        this.healthLogRepository = healthLogRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/log/{userId}")
    @ResponseBody
    public ResponseEntity<?> logData(@PathVariable Long userId, @RequestBody HealthLog log) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.badRequest().body("User not found");

        if (log.getDate() == null) log.setDate(LocalDate.now());

        HealthLog saved = healthLogRepository.findByUserAndDate(user, log.getDate())
                .map(existing -> {
                    existing.setSteps(log.getSteps());
                    existing.setWaterIntakeLiters(log.getWaterIntakeLiters());
                    existing.setCalorieIntake(log.getCalorieIntake());
                    return healthLogRepository.save(existing);
                })
                .orElseGet(() -> {
                    log.setUser(user);
                    return healthLogRepository.save(log);
                });

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/logs/{userId}")
    @ResponseBody
    public List<HealthLog> getUserLogs(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return healthLogRepository.findByUserOrderByDateDesc(user);
    }
}