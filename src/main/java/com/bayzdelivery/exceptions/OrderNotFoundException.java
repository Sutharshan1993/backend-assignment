package com.bayzdelivery.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String s) {
        super(s);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
