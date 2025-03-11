package com.bayzdelivery.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Represents the response for a delivery operation.
 * <p>
 * This record encapsulates data related to a specific delivery, including identifying
 * information, distance, timing details, delivery status, and commission information.
 * <p>
 * Fields:
 * - id: Unique identifier of the delivery.
 * - deliveryManId: Unique identifier of the delivery person responsible for the delivery.
 * - orderId: Unique identifier of the associated order.
 * - distance: The distance covered for the delivery in kilometers.
 * - startTime: The start time when the delivery began.
 * - endTime: The end time when the delivery was completed.
 * - status: Current status of the delivery (e.g., "Pending", "Completed").
 * - commission: The commission earned by the delivery person for this delivery.
 * <p>
 * Constructors:
 * - Full constructor for initializing all fields with specific values.
 * - Constructor that supports initialization with basic delivery details such as id, deliveryManId,
 * startTime, and status. Other fields are initialized with default values.
 * - Constructor allowing initialization with additional time, commission, and distance details,
 * while supporting conversion of time values from Instant to LocalDateTime.
 * <p>
 * Utility Methods:
 * - convertInstantToLocalDateTime: Converts an Instant to LocalDateTime using the system default time zone.
 */
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
