package com.example.numerical.service;

import com.example.numerical.model.CalculationRequest;
import com.example.numerical.model.CalculationResult;

public interface NumericalMethod {
    CalculationResult calculate(CalculationRequest request);
}
