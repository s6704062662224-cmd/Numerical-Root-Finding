package com.example.numerical.model;

public class CalculationRequest {
    private String method;
    private String function;
    private double xl;
    private double xu;
    private double x0;
    private double x1;
    private double tolerance = 0.000001;
    private int maxIterations = 100;

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getFunction() { return function; }
    public void setFunction(String function) { this.function = function; }
    public double getXl() { return xl; }
    public void setXl(double xl) { this.xl = xl; }
    public double getXu() { return xu; }
    public void setXu(double xu) { this.xu = xu; }
    public double getX0() { return x0; }
    public void setX0(double x0) { this.x0 = x0; }
    public double getX1() { return x1; }
    public void setX1(double x1) { this.x1 = x1; }
    public double getTolerance() { return tolerance; }
    public void setTolerance(double tolerance) { this.tolerance = tolerance; }
    public int getMaxIterations() { return maxIterations; }
    public void setMaxIterations(int maxIterations) { this.maxIterations = maxIterations; }
}
