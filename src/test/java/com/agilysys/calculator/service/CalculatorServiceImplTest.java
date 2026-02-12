package com.agilysys.calculator.service;

import com.agilysys.calculator.dto.CalculatorRequest;
import com.agilysys.calculator.dto.CalculatorResponse;
import com.agilysys.calculator.dto.Operation;
import com.agilysys.calculator.exception.DivisionByZeroException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CalculatorServiceImpl.
 */
@DisplayName("Calculator Service Tests")
class CalculatorServiceImplTest {
    
    private CalculatorService calculatorService;
    
    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorServiceImpl();
    }
    
    @Test
    @DisplayName("Should add two positive numbers")
    void testAdd_PositiveNumbers() {
        Double result = calculatorService.add(10.5, 5.2);
        assertEquals(15.7, result, 0.001);
    }
    
    @Test
    @DisplayName("Should add negative numbers")
    void testAdd_NegativeNumbers() {
        Double result = calculatorService.add(-10.0, -5.0);
        assertEquals(-15.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should subtract two numbers")
    void testSubtract() {
        Double result = calculatorService.subtract(10.0, 5.0);
        assertEquals(5.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should subtract resulting in negative number")
    void testSubtract_Negative() {
        Double result = calculatorService.subtract(5.0, 10.0);
        assertEquals(-5.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should multiply two numbers")
    void testMultiply() {
        Double result = calculatorService.multiply(10.0, 5.0);
        assertEquals(50.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should multiply by zero")
    void testMultiply_ByZero() {
        Double result = calculatorService.multiply(10.0, 0.0);
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should divide two numbers")
    void testDivide() {
        Double result = calculatorService.divide(10.0, 5.0);
        assertEquals(2.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void testDivide_ByZero_ThrowsException() {
        DivisionByZeroException exception = assertThrows(
            DivisionByZeroException.class,
            () -> calculatorService.divide(10.0, 0.0)
        );
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }
    
    @ParameterizedTest
    @CsvSource({
        "10.0, 5.0, ADD, 15.0",
        "10.0, 5.0, SUBTRACT, 5.0",
        "10.0, 5.0, MULTIPLY, 50.0",
        "10.0, 5.0, DIVIDE, 2.0"
    })
    @DisplayName("Should perform calculation for all operations")
    void testCalculate_AllOperations(Double num1, Double num2, Operation operation, Double expected) {
        CalculatorRequest request = new CalculatorRequest(num1, num2, operation);
        CalculatorResponse response = calculatorService.calculate(request);
        
        assertNotNull(response);
        assertEquals(expected, response.result(), 0.001);
        assertEquals(operation.name(), response.operation());
    }
    
    @Test
    @DisplayName("Should handle decimal precision")
    void testCalculate_DecimalPrecision() {
        CalculatorRequest request = new CalculatorRequest(10.555, 5.225, Operation.ADD);
        CalculatorResponse response = calculatorService.calculate(request);
        
        assertEquals(15.78, response.result(), 0.001);
    }
    
    @Test
    @DisplayName("Should handle large numbers")
    void testCalculate_LargeNumbers() {
        CalculatorRequest request = new CalculatorRequest(1000000.0, 2000000.0, Operation.MULTIPLY);
        CalculatorResponse response = calculatorService.calculate(request);
        
        assertEquals(2000000000000.0, response.result(), 0.001);
    }
    
    @Test
    @DisplayName("Should handle very small numbers")
    void testCalculate_SmallNumbers() {
        CalculatorRequest request = new CalculatorRequest(0.001, 0.002, Operation.ADD);
        CalculatorResponse response = calculatorService.calculate(request);
        
        assertEquals(0.003, response.result(), 0.0001);
    }
}
