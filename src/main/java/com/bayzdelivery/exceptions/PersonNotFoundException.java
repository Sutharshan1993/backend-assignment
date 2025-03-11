package com.bayzdelivery.exceptions;

/**
 * Exception thrown when a specific person-related entity cannot be found.
 * This exception is used to indicate scenarios where an operation fails
 * due to the absence of the requested person-related resource.
 * <p>
 * This exception is meant to be caught and handled within the application
 * to provide meaningful feedback to the client or user.
 */
public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}