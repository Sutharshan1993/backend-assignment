package com.bayzdelivery.dto;

import java.time.LocalDateTime;

/**
 * Represents the response object for an order.
 * <p>
 * This record encapsulates the key details of an order including its unique identifier,
 * name, price, associated customer, and the time the order was created.
 * <p>
 * Fields:
 * - id: Unique identifier of the order.
 * - orderName: The name or title of the order.
 * - orderPrice: The price of the order.
 * - customer: The name of the customer associated with the order.
 * - orderTime: The timestamp when the order was created. If not provided, it's initialized as the current time.
 * <p>
 * Constructors:
 * - Full constructor for initializing all fields explicitly.
 * - Alternative constructor initializing the order with current time if the order time is not provided.
 */
public record OrderResponse(Long id, String orderName, double orderPrice, String customer, LocalDateTime orderTime) {
    public OrderResponse(Long id, String orderName, double orderPrice, String name) {
        this(id, orderName, orderPrice, name, LocalDateTime.now());
    }
}
