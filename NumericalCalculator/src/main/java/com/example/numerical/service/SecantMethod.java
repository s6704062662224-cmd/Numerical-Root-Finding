package com.example.numerical.service;

import com.example.numerical.model.*;
import java.util.List;

public class SecantMethod extends AbstractNumericalMethod {
    @Override
    public CalculationResult calculate(CalculationRequest r) {
        validate(r);
        double x0 = r.getX0(), x1 = r.getX1();
        if (!Double.isFinite(x0) || !Double.isFinite(x1) || x0 == x1)
            throw new IllegalArgumentException("X0 และ X1 ต้องเป็นค่าที่ต่างกัน");

        List<Iteration> rows = newRows();
        for (int i = 1; i <= r.getMaxIterations(); i++) {
            double f0 = finite(f(r.getFunction(), x0));
            double f1 = finite(f(r.getFunction(), x1));
            if (Math.abs(f1) <= r.getTolerance()) {
                rows.add(new Iteration(i, x1, f1, 0));
                break;
            }

            double denominator = f1 - f0;
            if (Math.abs(denominator) < 1e-15)
                throw new IllegalArgumentException("ตัวหารใกล้ศูนย์ กรุณาเปลี่ยน X0 และ X1");

            double x2 = finite(x1 - f1 * (x1 - x0) / denominator);
            double fx2 = finite(f(r.getFunction(), x2));
            double error = Math.abs(x2 - x1);
            rows.add(new Iteration(i, x2, fx2, error));
            x0 = x1;
            x1 = x2;
            if (Math.abs(fx2) <= r.getTolerance() || error <= r.getTolerance()) break;
        }
        return result(x1, r.getFunction(), rows, "Secant Method completed");
    }
}
