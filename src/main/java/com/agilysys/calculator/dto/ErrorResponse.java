package com.agilysys.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard error response DTO.
 * 
 * @param status HTTP status code
 * @param message Error message
 * @param timestamp Timestamp of the error
 * @param errors List of specific field errors (for validation errors)
 */
@Schema(description = "Error response object")
public record ErrorResponse(
    
    @Schema(description = "HTTP status code", example = "400")
    int status,
    
    @Schema(description = "Error message", example = "Division by zero is not allowed")
    String message,
    
    @Schema(description = "Error timestamp", example = "2026-02-12T10:30:00")
    LocalDateTime timestamp,
    
    @Schema(description = "List of validation errors")
    List<String> errors
) {}
