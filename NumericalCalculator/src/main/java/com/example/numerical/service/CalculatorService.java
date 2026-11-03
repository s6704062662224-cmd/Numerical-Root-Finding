package com.example.numerical.service;

import com.example.numerical.model.*;
import com.example.numerical.repository.CalculationHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    private final CalculationHistoryRepository historyRepository;

    public CalculatorService(CalculationHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public CalculationResult calculate(CalculationRequest request) {
        if (request.getMethod() == null)
            throw new IllegalArgumentException("กรุณาเลือกวิธีคำนวณ");

        NumericalMethod method = switch (request.getMethod().toLowerCase()) {
            case "bisection" -> new BisectionMethod();
            case "falseposition" -> new FalsePositionMethod();
            case "newtonraphson" -> new NewtonRaphsonMethod();
            case "secant" -> new SecantMethod();
            default -> throw new IllegalArgumentException("ไม่พบวิธีคำนวณนี้");
        };

        CalculationResult result = method.calculate(request);
        historyRepository.save(new CalculationHistory(
                request.getMethod(), request.getFunction(), result.getRoot(),
                result.getFx(), result.getIterations().size()));
        return result;
    }
}
