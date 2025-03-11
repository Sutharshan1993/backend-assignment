package com.bayzdelivery.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.AbstractMap;

/**
 * Global exception handler for managing exceptions across the entire application.
 * <p>
 * This class provides centralized exception handling for various types of exceptions.
 * It uses Spring's @ControllerAdvice and @ExceptionHandler annotations to handle exceptions
 * thrown in controllers or services and return appropriate HTTP responses.
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @param exception
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(Exception exception) {
        LOG.error("Request could not be processed: ", exception);
        AbstractMap.SimpleEntry<String, String> response =
                new AbstractMap.SimpleEntry<>("message", "Request could not be processed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param ex
     * @return
     */
    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<String> handleDeliveryNotFoundExceptionException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * @param ex
     * @return
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundExceptionException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * @param ex
     * @return
     */
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFoundExceptionException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
