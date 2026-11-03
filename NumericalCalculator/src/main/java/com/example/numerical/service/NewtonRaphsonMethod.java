package com.example.numerical.service;

import com.example.numerical.model.*;
import java.util.List;

public class NewtonRaphsonMethod extends AbstractNumericalMethod {
    @Override
    public CalculationResult calculate(CalculationRequest r) {
        validate(r);
        double x = r.getX0();
        if (!Double.isFinite(x)) throw new IllegalArgumentException("ค่า X0 ไม่ถูกต้อง");

        List<Iteration> rows = newRows();
        for (int i = 1; i <= r.getMaxIterations(); i++) {
            double fx = finite(f(r.getFunction(), x));
            if (Math.abs(fx) <= r.getTolerance()) {
                rows.add(new Iteration(i, x, fx, 0));
                break;
            }

            double dfx = finite(derivative(r.getFunction(), x));
            if (Math.abs(dfx) < 1e-12)
                throw new IllegalArgumentException("อนุพันธ์ใกล้ศูนย์ กรุณาเปลี่ยนค่า X0");

            double next = finite(x - fx / dfx);
            double error = Math.abs(next - x);
            double nextFx = finite(f(r.getFunction(), next));
            rows.add(new Iteration(i, next, nextFx, error));
            x = next;
            if (Math.abs(nextFx) <= r.getTolerance() || error <= r.getTolerance()) break;
        }
        return result(x, r.getFunction(), rows, "Newton-Raphson Method completed");
    }
}
