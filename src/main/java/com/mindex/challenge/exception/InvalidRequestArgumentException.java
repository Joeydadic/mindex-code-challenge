package com.mindex.challenge.exception;

public class InvalidRequestArgumentException extends RuntimeException {
    
    /*
     * Custom exception handling for invalid parameter arguments for requests.
     */
    public InvalidRequestArgumentException(String message) {
        super(message);
    }
}

