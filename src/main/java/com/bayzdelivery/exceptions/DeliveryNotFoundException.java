package com.bayzdelivery.exceptions;

/**
 * Exception thrown when a delivery-related entity cannot be found.
 * <p>
 * This exception is used in scenarios where an attempted operation
 * on a delivery resource cannot be completed because the specified
 * delivery entity does not exist or is unavailable.
 * <p>
 * This exception extends RuntimeException, making it an unchecked
 * exception that can be optionally caught and handled in the application.
 */
public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(String message) {
        super(message);
    }

    public DeliveryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
