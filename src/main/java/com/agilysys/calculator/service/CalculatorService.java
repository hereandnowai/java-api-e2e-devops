package com.agilysys.calculator.service;

import com.agilysys.calculator.dto.CalculatorRequest;
import com.agilysys.calculator.dto.CalculatorResponse;

/**
 * Service interface for calculator operations.
 */
public interface CalculatorService {
    
    /**
     * Performs the requested calculation.
     *
     * @param request The calculation request containing numbers and operation
     * @return CalculatorResponse containing the result
     * @throws com.agilysys.calculator.exception.DivisionByZeroException if dividing by zero
     * @throws com.agilysys.calculator.exception.InvalidOperationException if operation is invalid
     */
    CalculatorResponse calculate(CalculatorRequest request);
    
    /**
     * Adds two numbers.
     *
     * @param num1 First number
     * @param num2 Second number
     * @return The sum
     */
    Double add(Double num1, Double num2);
    
    /**
     * Subtracts the second number from the first.
     *
     * @param num1 First number
     * @param num2 Second number
     * @return The difference
     */
    Double subtract(Double num1, Double num2);
    
    /**
     * Multiplies two numbers.
     *
     * @param num1 First number
     * @param num2 Second number
     * @return The product
     */
    Double multiply(Double num1, Double num2);
    
    /**
     * Divides the first number by the second.
     *
     * @param num1 First number (dividend)
     * @param num2 Second number (divisor)
     * @return The quotient
     * @throws com.agilysys.calculator.exception.DivisionByZeroException if num2 is zero
     */
    Double divide(Double num1, Double num2);
}
