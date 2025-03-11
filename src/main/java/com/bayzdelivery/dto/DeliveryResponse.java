package com.bayzdelivery.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
        this(id, deliveryManId, null, 0.0, convertInstantToLocalDateTime(startTime), null, status, 0.0);
    }

    public DeliveryResponse(Long id, Long deliveryManId, @NotNull Instant startTime, @NotNull Instant endTime, String status, double commission, Double distance) {
        this(id, deliveryManId, null, distance, convertInstantToLocalDateTime(startTime), endTime != null ? convertInstantToLocalDateTime(endTime) : null, status, commission);
    }

    private static LocalDateTime convertInstantToLocalDateTime(Instant instant) {
        if ( instant == null ) {
            return null;
        }
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
