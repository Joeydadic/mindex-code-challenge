package com.mindex.challenge.exception;

public class RequestNotFoundException extends RuntimeException {
    
    /*
     * Custom exception handling for not found errors occur when performing Get, Find, and other
     * data retrieval methods.
     */
    public RequestNotFoundException(String message) {
        super(message);
    }
}
