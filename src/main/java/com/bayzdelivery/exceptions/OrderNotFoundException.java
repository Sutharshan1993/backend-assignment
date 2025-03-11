package com.bayzdelivery.exceptions;

/**
 * Exception thrown to indicate that a specific order could not be found.
 * <p>
 * This exception is typically thrown in scenarios where an attempted
 * operation on an order cannot proceed because the specified order does not exist.
 * <p>
 * It extends RuntimeException, indicating that it is an unchecked exception.
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String s) {
        super(s);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
