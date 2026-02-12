package com.agilysys.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for calculator operations.
 * 
 * @param num1 First number for the operation
 * @param num2 Second number for the operation
 * @param operation The operation to perform (ADD, SUBTRACT, MULTIPLY, DIVIDE)
 */
@Schema(description = "Request object for calculator operations")
public record CalculatorRequest(
    
    @NotNull(message = "First number is required")
    @Schema(description = "First operand", example = "10.5", requiredMode = Schema.RequiredMode.REQUIRED)
    Double num1,
    
    @NotNull(message = "Second number is required")
    @Schema(description = "Second operand", example = "5.2", requiredMode = Schema.RequiredMode.REQUIRED)
    Double num2,
    
    @NotNull(message = "Operation is required")
    @Schema(description = "Operation to perform", example = "ADD", requiredMode = Schema.RequiredMode.REQUIRED)
    Operation operation
) {}
