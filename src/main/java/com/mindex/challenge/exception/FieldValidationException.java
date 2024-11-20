package com.mindex.challenge.exception;

import java.util.List;

public class FieldValidationException extends RuntimeException {
    
    private final List<String> errorMessages;
    /*
     * Custom exception handling for invalid request body.
     */
    public FieldValidationException(List<String> errorMessages) {
        super("One or more invalid fields provided.");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return this.errorMessages;
    }
}
