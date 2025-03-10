package com.bayzdelivery.dto;

import com.bayzdelivery.model.Person;

import java.time.LocalDateTime;

public record OrderResponse(Long id, String orderName, double orderPrice, Person customer, LocalDateTime orderTime) {
}
