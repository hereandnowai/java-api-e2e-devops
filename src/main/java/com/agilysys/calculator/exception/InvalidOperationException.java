package com.agilysys.calculator.exception;

/**
 * Exception thrown when an invalid operation is requested.
 */
public class InvalidOperationException extends RuntimeException {
    
    public InvalidOperationException(String message) {
        super(message);
    }
}
