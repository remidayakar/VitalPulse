package com.example.devops_project.repository;

import com.example.devops_project.domain.HealthLog;
import com.example.devops_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HealthLogRepository extends JpaRepository<HealthLog, Long> {
    List<HealthLog> findByUserOrderByDateDesc(User user);
    Optional<HealthLog> findByUserAndDate(User user, LocalDate date);
}