package com.agilysys.calculator.controller;

import com.agilysys.calculator.dto.CalculatorRequest;
import com.agilysys.calculator.dto.CalculatorResponse;
import com.agilysys.calculator.dto.Operation;
import com.agilysys.calculator.exception.DivisionByZeroException;
import com.agilysys.calculator.service.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for CalculatorController.
 */
@WebMvcTest(CalculatorController.class)
@DisplayName("Calculator Controller Tests")
class CalculatorControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CalculatorService calculatorService;
    
    @Test
    @DisplayName("Should return 200 OK for valid addition request")
    void testCalculate_ValidAddition_Returns200() throws Exception {
        // Arrange
        CalculatorRequest request = new CalculatorRequest(10.5, 5.2, Operation.ADD);
        CalculatorResponse response = new CalculatorResponse(15.7, "ADD");
        
        when(calculatorService.calculate(any(CalculatorRequest.class))).thenReturn(response);
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(15.7))
                .andExpect(jsonPath("$.operation").value("ADD"));
    }
    
    @Test
    @DisplayName("Should return 200 OK for valid subtraction request")
    void testCalculate_ValidSubtraction_Returns200() throws Exception {
        // Arrange
        CalculatorRequest request = new CalculatorRequest(10.0, 5.0, Operation.SUBTRACT);
        CalculatorResponse response = new CalculatorResponse(5.0, "SUBTRACT");
        
        when(calculatorService.calculate(any(CalculatorRequest.class))).thenReturn(response);
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5.0))
                .andExpect(jsonPath("$.operation").value("SUBTRACT"));
    }
    
    @Test
    @DisplayName("Should return 200 OK for valid multiplication request")
    void testCalculate_ValidMultiplication_Returns200() throws Exception {
        // Arrange
        CalculatorRequest request = new CalculatorRequest(10.0, 5.0, Operation.MULTIPLY);
        CalculatorResponse response = new CalculatorResponse(50.0, "MULTIPLY");
        
        when(calculatorService.calculate(any(CalculatorRequest.class))).thenReturn(response);
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(50.0))
                .andExpect(jsonPath("$.operation").value("MULTIPLY"));
    }
    
    @Test
    @DisplayName("Should return 200 OK for valid division request")
    void testCalculate_ValidDivision_Returns200() throws Exception {
        // Arrange
        CalculatorRequest request = new CalculatorRequest(10.0, 5.0, Operation.DIVIDE);
        CalculatorResponse response = new CalculatorResponse(2.0, "DIVIDE");
        
        when(calculatorService.calculate(any(CalculatorRequest.class))).thenReturn(response);
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(2.0))
                .andExpect(jsonPath("$.operation").value("DIVIDE"));
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request when dividing by zero")
    void testCalculate_DivisionByZero_Returns400() throws Exception {
        // Arrange
        CalculatorRequest request = new CalculatorRequest(10.0, 0.0, Operation.DIVIDE);
        
        when(calculatorService.calculate(any(CalculatorRequest.class)))
            .thenThrow(new DivisionByZeroException("Division by zero is not allowed"));
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Division by zero is not allowed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request when num1 is null")
    void testCalculate_NullNum1_Returns400() throws Exception {
        // Arrange
        String requestJson = "{\"num1\": null, \"num2\": 5.0, \"operation\": \"ADD\"}";
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request when num2 is null")
    void testCalculate_NullNum2_Returns400() throws Exception {
        // Arrange
        String requestJson = "{\"num1\": 10.0, \"num2\": null, \"operation\": \"ADD\"}";
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request when operation is null")
    void testCalculate_NullOperation_Returns400() throws Exception {
        // Arrange
        String requestJson = "{\"num1\": 10.0, \"num2\": 5.0, \"operation\": null}";
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request when request body is empty")
    void testCalculate_EmptyBody_Returns400() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Should handle negative numbers correctly")
    void testCalculate_NegativeNumbers_Returns200() throws Exception {
        // Arrange
        CalculatorRequest request = new CalculatorRequest(-10.0, -5.0, Operation.ADD);
        CalculatorResponse response = new CalculatorResponse(-15.0, "ADD");
        
        when(calculatorService.calculate(any(CalculatorRequest.class))).thenReturn(response);
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(-15.0))
                .andExpect(jsonPath("$.operation").value("ADD"));
    }
}
