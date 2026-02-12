package com.agilysys.calculator.controller;

import com.agilysys.calculator.dto.CalculatorRequest;
import com.agilysys.calculator.dto.CalculatorResponse;
import com.agilysys.calculator.dto.ErrorResponse;
import com.agilysys.calculator.service.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for calculator operations.
 * Provides endpoints for basic arithmetic calculations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/calculator")
@RequiredArgsConstructor
@Tag(name = "Calculator", description = "Calculator API for basic arithmetic operations")
public class CalculatorController {
    
    private final CalculatorService calculatorService;
    
    /**
     * Performs a calculation based on the provided request.
     *
     * @param request The calculation request containing operands and operation
     * @return ResponseEntity containing the calculation result
     */
    @PostMapping("/calculate")
    @Operation(
        summary = "Perform calculation",
        description = "Performs arithmetic operation (ADD, SUBTRACT, MULTIPLY, DIVIDE) on two numbers"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Calculation successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CalculatorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - validation error or division by zero",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    public ResponseEntity<CalculatorResponse> calculate(@Valid @RequestBody CalculatorRequest request) {
        log.info("Received calculation request: {}", request);
        CalculatorResponse response = calculatorService.calculate(request);
        return ResponseEntity.ok(response);
    }
}
