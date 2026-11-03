package com.example.numerical.model;

public class Iteration {
    private final int iteration;
    private final double x;
    private final double fx;
    private final double error;

    public Iteration(int iteration, double x, double fx, double error) {
        this.iteration = iteration;
        this.x = x;
        this.fx = fx;
        this.error = error;
    }

    public int getIteration() { return iteration; }
    public double getX() { return x; }
    public double getFx() { return fx; }
    public double getError() { return error; }
}
