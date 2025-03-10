package com.bayzdelivery.dto;

import java.time.LocalDateTime;

public record OrderResponse(Long id, String orderName, double orderPrice, String customer, LocalDateTime orderTime) {
    public OrderResponse(Long id, String orderName, double orderPrice, String name) {
        this(id, orderName, orderPrice, name, LocalDateTime.now());
    }
}
