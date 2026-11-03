package com.example.numerical.service;

import com.example.numerical.model.*;
import java.util.List;

public class BisectionMethod extends AbstractNumericalMethod {
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

        double mid = a, error = Double.POSITIVE_INFINITY;
        for (int i = 1; i <= r.getMaxIterations(); i++) {
            mid = a + (b - a) / 2.0;
            double fm = finite(f(r.getFunction(), mid));
            error = Math.abs(b - a) / 2.0;
            rows.add(new Iteration(i, mid, fm, error));
            if (Math.abs(fm) <= r.getTolerance() || error <= r.getTolerance()) break;
            if (Math.signum(fa) != Math.signum(fm)) {
                b = mid;
                fb = fm;
            } else {
                a = mid;
                fa = fm;
            }
        }
        return result(mid, r.getFunction(), rows, "Bisection Method completed");
    }
}
