package com.example.numerical.service;

import com.example.numerical.model.*;
import java.util.List;

public class FalsePositionMethod extends AbstractNumericalMethod {
    @Override
    public CalculationResult calculate(CalculationRequest r) {
        validate(r);
        double a = r.getXl(), b = r.getXu();
        if (!Double.isFinite(a) || !Double.isFinite(b) || a >= b)
            throw new IllegalArgumentException("ต้องกำหนด XL < XU");

        double fa = finite(f(r.getFunction(), a));
        double fb = finite(f(r.getFunction(), b));
        if (fa != 0 && fb != 0 && Math.signum(fa) == Math.signum(fb))
            throw new IllegalArgumentException("f(XL) และ f(XU) ต้องมีเครื่องหมายต่างกัน");

        List<Iteration> rows = newRows();
        if (fa == 0) {
            rows.add(new Iteration(1, a, fa, 0));
            return result(a, r.getFunction(), rows, "พบรากที่ XL");
        }
        if (fb == 0) {
            rows.add(new Iteration(1, b, fb, 0));
            return result(b, r.getFunction(), rows, "พบรากที่ XU");
        }

        double x = a, previous = Double.NaN;
        for (int i = 1; i <= r.getMaxIterations(); i++) {
            double denominator = fb - fa;
            if (Math.abs(denominator) < 1e-15)
                throw new IllegalArgumentException("ตัวหารใกล้ศูนย์ ไม่สามารถคำนวณต่อได้");

            x = finite((a * fb - b * fa) / denominator);
            double fx = finite(f(r.getFunction(), x));
            double error = Double.isNaN(previous) ? Math.abs(b - a) : Math.abs(x - previous);
            rows.add(new Iteration(i, x, fx, error));
            if (Math.abs(fx) <= r.getTolerance() ||
                (!Double.isNaN(previous) && error <= r.getTolerance())) break;

            if (Math.signum(fa) != Math.signum(fx)) {
                b = x;
                fb = fx;
            } else {
                a = x;
                fa = fx;
            }
            previous = x;
        }
        return result(x, r.getFunction(), rows, "False Position Method completed");
    }
}
