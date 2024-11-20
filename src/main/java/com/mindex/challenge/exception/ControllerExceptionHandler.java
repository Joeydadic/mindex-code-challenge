package com.mindex.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Error handler for invalid fields in domain objects from request body input.
     * Returned as a response when request body objects contain invalid fields.
     * @param FieldValidationException
     */
    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<?> handleFieldValidationError(FieldValidationException fieldValidationException) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "One or more fields in the request body has failed validation.");
        response.put("message", fieldValidationException.getErrorMessages());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error handler for individual invalid request parameter arguments.
     * Returned as a response when request parameter contains null or blank property.
     * @param invalidRequestArgumentException
     */
    @ExceptionHandler(InvalidRequestArgumentException.class)
    public ResponseEntity<?> handleInvalidRequestArgumentError(InvalidRequestArgumentException invalidRequestArgumentException) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Invalid input provided.");
        response.put("message", invalidRequestArgumentException.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error handler for requested data not found in our respective repository.
     * Returned as a response when querying for data provided the given input is not found.
     * @param requestNotFoundException
     */
    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<?> handleRequestNotFoundError(RequestNotFoundException requestNotFoundException) {

        Map<String, Object> response = new HashMap<>();
        
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Requested data was not found.");
        response.put("message", requestNotFoundException.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles unexpected internal errors, catch all handler to return to the user that there was an unexpected error for their request.
     */
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<?> handleUnexpectedInternalError() {

        Map<String, Object> response = new HashMap<>();

        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal error occurred. Please try again later or contact Server Administrator.");
        response.put("message", "Unexpected error occurred.");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*If was applicable, methods for handling permissions and authorizations would reside here for handling*/
    
}
