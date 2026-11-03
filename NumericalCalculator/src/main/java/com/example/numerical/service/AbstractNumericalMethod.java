package com.example.numerical.service;

import com.example.numerical.model.*;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNumericalMethod implements NumericalMethod {
    protected double f(String function, double x) {
        Expression expression = new ExpressionBuilder(function).variable("x").build();
        return expression.setVariable("x", x).evaluate();
    }

    protected double derivative(String function, double x) {
        double h = 0.000001;
        return (f(function, x + h) - f(function, x - h)) / (2 * h);
    }

    protected List<Iteration> newRows() {
        return new ArrayList<>();
    }

    protected CalculationResult result(double root, String function,
                                       List<Iteration> rows, String message) {
        return new CalculationResult(root, f(function, root), rows, message);
    }

    protected void validate(CalculationRequest r) {
        if (r.getFunction() == null || r.getFunction().isBlank()) {
            throw new IllegalArgumentException("กรุณากรอกสมการ");
        }
        if (!Double.isFinite(r.getTolerance()) || r.getTolerance() <= 0) {
            throw new IllegalArgumentException("Tolerance ต้องมากกว่า 0");
        }
        if (r.getMaxIterations() < 1 || r.getMaxIterations() > 10000) {
            throw new IllegalArgumentException("จำนวน Iterations ต้องอยู่ระหว่าง 1 ถึง 10000");
        }
        f(r.getFunction(), 0);
    }

    protected double finite(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("คำนวณไม่ได้ กรุณาตรวจสอบสมการและค่าเริ่มต้น");
        }
        return value;
    }
}
