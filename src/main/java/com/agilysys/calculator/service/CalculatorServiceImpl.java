package com.agilysys.calculator.service;

import com.agilysys.calculator.dto.CalculatorRequest;
import com.agilysys.calculator.dto.CalculatorResponse;
import com.agilysys.calculator.exception.DivisionByZeroException;
import com.agilysys.calculator.exception.InvalidOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CalculatorService interface.
 * Provides business logic for calculator operations.
 */
@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {
    
    @Override
    public CalculatorResponse calculate(CalculatorRequest request) {
        log.info("Performing calculation: {} {} {}", 
            request.num1(), request.operation(), request.num2());
        
        Double result = switch (request.operation()) {
            case ADD -> add(request.num1(), request.num2());
            case SUBTRACT -> subtract(request.num1(), request.num2());
            case MULTIPLY -> multiply(request.num1(), request.num2());
            case DIVIDE -> divide(request.num1(), request.num2());
            default -> throw new InvalidOperationException(
                "Invalid operation: " + request.operation());
        };
        
        log.info("Calculation result: {}", result);
        return new CalculatorResponse(result, request.operation().name());
    }
    
    @Override
    public Double add(Double num1, Double num2) {
        return num1 + num2;
    }
    
    @Override
    public Double subtract(Double num1, Double num2) {
        return num1 - num2;
    }
    
    @Override
    public Double multiply(Double num1, Double num2) {
        return num1 * num2;
    }
    
    @Override
    public Double divide(Double num1, Double num2) {
        if (num2 == 0.0) {
            log.error("Attempted division by zero");
            throw new DivisionByZeroException("Division by zero is not allowed");
        }
        return num1 / num2;
    }
}
