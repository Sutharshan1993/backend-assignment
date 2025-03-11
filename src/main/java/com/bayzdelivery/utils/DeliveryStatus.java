package com.bayzdelivery.utils;

/**
 * Enum representing the possible statuses of a delivery process.
 * <p>
 * This enum defines the lifecycle states of a delivery, from creation
 * to completion. It can be used to track and manage the current state
 * of a delivery in the system.
 */
public enum DeliveryStatus {
    CREATED,
    ACTIVE, // Delivery is in progress
    COMPLETED // Delivery is completed
}
