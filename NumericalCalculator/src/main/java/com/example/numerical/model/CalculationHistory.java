package com.example.numerical.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calculation_history")
public class CalculationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;
    @Column(length = 500)
    private String functionText;
    private double root;
    private double fx;
    private int iterationCount;
    private LocalDateTime createdAt;

    public CalculationHistory() {}

    public CalculationHistory(String method, String functionText, double root,
                              double fx, int iterationCount) {
        this.method = method;
        this.functionText = functionText;
        this.root = root;
        this.fx = fx;
        this.iterationCount = iterationCount;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getMethod() { return method; }
    public String getFunctionText() { return functionText; }
    public double getRoot() { return root; }
    public double getFx() { return fx; }
    public int getIterationCount() { return iterationCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
