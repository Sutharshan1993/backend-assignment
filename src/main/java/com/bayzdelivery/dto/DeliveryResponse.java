package com.bayzdelivery.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;

public record DeliveryResponse(
        Long id,
        Long deliveryManId,
        Long orderId,
        double distance,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status,
        double commission
) {

    public DeliveryResponse(Long id, Long deliveryManId, @NotNull Instant startTime, String status) {
        this(id, deliveryManId, null, 0.0, LocalDateTime.from(startTime), null, status, 0.0);
    }

    public DeliveryResponse(Long id, Long deliveryManId, @NotNull Instant startTime, @NotNull Instant endTime, String status, double commission, Double distance) {
        this(id, deliveryManId, null, distance, LocalDateTime.from(startTime), LocalDateTime.from(endTime), status, commission);
    }
}
