package com.example.numerical.model;

import java.util.List;

public class CalculationResult {
    private final double root;
    private final double fx;
    private final List<Iteration> iterations;
    private final String message;

    public CalculationResult(double root, double fx, List<Iteration> iterations, String message) {
        this.root = root;
        this.fx = fx;
        this.iterations = iterations;
        this.message = message;
    }

    public double getRoot() { return root; }
    public double getFx() { return fx; }
    public List<Iteration> getIterations() { return iterations; }
    public String getMessage() { return message; }
}
