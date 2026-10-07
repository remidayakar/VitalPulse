package com.example.devops_project.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "health_logs")
public class HealthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private int steps;
    private double waterIntakeLiters;
    private int calorieIntake;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public HealthLog() {}

    public HealthLog(LocalDate date, int steps, double waterIntakeLiters, int calorieIntake, User user) {
        this.date = date;
        this.steps = steps;
        this.waterIntakeLiters = waterIntakeLiters;
        this.calorieIntake = calorieIntake;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = steps; }

    public double getWaterIntakeLiters() { return waterIntakeLiters; }
    public void setWaterIntakeLiters(double waterIntakeLiters) { this.waterIntakeLiters = waterIntakeLiters; }

    public int getCalorieIntake() { return calorieIntake; }
    public void setCalorieIntake(int calorieIntake) { this.calorieIntake = calorieIntake; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}