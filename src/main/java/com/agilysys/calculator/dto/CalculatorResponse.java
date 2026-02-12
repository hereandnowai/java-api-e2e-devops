package com.agilysys.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for calculator operations.
 * 
 * @param result The calculated result
 * @param operation The operation that was performed
 */
@Schema(description = "Response object containing calculation result")
public record CalculatorResponse(
    
    @Schema(description = "Calculation result", example = "15.7")
    Double result,
    
    @Schema(description = "Operation performed", example = "ADD")
    String operation
) {}
