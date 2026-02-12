package com.agilysys.calculator.exception;

/**
 * Exception thrown when attempting to divide by zero.
 */
public class DivisionByZeroException extends RuntimeException {
    
    public DivisionByZeroException(String message) {
        super(message);
    }
}
