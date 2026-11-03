package com.example.numerical.controller;

import com.example.numerical.model.*;
import com.example.numerical.repository.CalculationHistoryRepository;
import com.example.numerical.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final CalculationHistoryRepository historyRepository;

    public CalculatorController(CalculatorService calculatorService,
                                CalculationHistoryRepository historyRepository) {
        this.calculatorService = calculatorService;
        this.historyRepository = historyRepository;
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody CalculationRequest request) {
        try {
            return ResponseEntity.ok(calculatorService.calculate(request));
        } catch (IllegalArgumentException | ArithmeticException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage() == null ? "ไม่สามารถคำนวณได้" : e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "สมการไม่ถูกต้องหรือไม่สามารถคำนวณได้"));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> history() {
        return ResponseEntity.ok(historyRepository.findTop20ByOrderByCreatedAtDesc());
    }

    @DeleteMapping("/history")
    public ResponseEntity<?> clearHistory() {
        historyRepository.deleteAll();
        return ResponseEntity.ok(Map.of("message", "ลบประวัติแล้ว"));
    }
}
