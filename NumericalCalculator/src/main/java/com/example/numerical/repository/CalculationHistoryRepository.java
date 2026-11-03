package com.example.numerical.repository;

import com.example.numerical.model.CalculationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalculationHistoryRepository extends JpaRepository<CalculationHistory, Long> {
    List<CalculationHistory> findTop20ByOrderByCreatedAtDesc();
}
