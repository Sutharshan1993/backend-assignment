package com.bayzdelivery.service;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.utils.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Implementation of the DeliveryCheckService interface responsible for monitoring
 * and managing overdue deliveries. This service periodically evaluates active
 * deliveries and identifies those that have exceeded the allowed time threshold.
 * <p>
 * Key Responsibilities:
 * - Scheduling periodic checks for active deliveries using fixed-rate scheduling.
 * - Identifying deliveries flagged as overdue based on a configurable time threshold.
 * - Notifying the customer support team for any deliveries identified as overdue.
 * <p>
 * Dependencies:
 * - DeliveryRepository: Repository for querying delivery entities.
 * - `overdueThresholdMinutes`: Configurable time threshold for marking deliveries as overdue.
 * <p>
 * Configuration:
 * - `delivery-check.overdue-threshold-minutes`: Specifies the duration after which a delivery is considered overdue.
 * - `delivery-check.scheduled-fixed-rate-ms`: Defines the fixed rate interval for running the scheduled checks.
 * <p>
 * Methods:
 * - checkDelivery(): Scheduled method that evaluates ongoing deliveries, determines if they are overdue, and notifies customer support.
 * - notifyCustomerSupport(Delivery delivery): Notifies the customer support team about an overdue delivery, if applicable.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryCheckServiceImpl implements DeliveryCheckService {

    private final DeliveryRepository deliveryRepository;

    @Value("${delivery-check.overdue-threshold-minutes}")
    private int overdueThresholdMinutes;

    /**
     * Periodically checks for overdue deliveries and notifies customer support if necessary.
     * This method is scheduled to execute at a fixed rate defined in the configuration.
     * <p>
     * Functionality:
     * - Identifies deliveries with the status `ACTIVE` that have exceeded a specified overdue threshold.
     * - Logs relevant information regarding overdue deliveries.
     * - For each overdue delivery, triggers a notification to customer support.
     * <p>
     * Configuration:
     * - The fixed execution rate is defined by the property `delivery-check.scheduled-fixed-rate-ms`.
     * - The overdue threshold is defined by the property `delivery-check.overdue-threshold-minutes`.
     * <p>
     * Implementation Details:
     * - The method computes an "overdue time" by subtracting the configured threshold in minutes
     * from the current time.
     * - It queries the database using the `deliveryRepository` to find deliveries that match
     * the criteria: status `ACTIVE` and start time before the computed overdue time.
     * - For each delivery identified as overdue, the method invokes `notifyCustomerSupport` to
     * notify customer support for further action.
     * - Logs errors encountered during execution.
     * <p>
     * Related Components:
     * - `DeliveryRepository`: Used to query deliveries based on status and start time criteria.
     * - `DeliveryStatus`: Enum representing the status of deliveries (e.g., ACTIVE, CREATED).
     * - `notifyCustomerSupport(Delivery)`: Method responsible for handling notifications to customer support.
     */
    @Override
    @Scheduled(fixedRateString = "${delivery-check.scheduled-fixed-rate-ms}")
    public void checkDelivery() {
        log.info("Entered into checkDelivery");
        try {
            var overdueTime = Instant.now().minusSeconds(60L * overdueThresholdMinutes);
            log.info("Over Due delivery Check Time :{}", overdueTime);
            var overdueDeliveries = deliveryRepository.findByStatusAndStartTimeBefore(
                    DeliveryStatus.ACTIVE, overdueTime);
            if ( !overdueDeliveries.isEmpty() ) {
                overdueDeliveries.forEach(this::notifyCustomerSupport);
            }
        } catch ( Exception e ) {
            log.error("Error while checking overdue deliveries: {}", e.getMessage(), e);
        }
    }

    /**
     * Notifies the customer support team about an overdue delivery, if applicable.
     * Logs a warning if the delivery object is null. If a valid delivery is provided,
     * logs the delivery ID and start time, indicating that it requires customer support attention.
     *
     * @param delivery The delivery entity that is flagged as overdue. If null, no notification is performed.
     */
    void notifyCustomerSupport(Delivery delivery) {
        if ( delivery == null ) {
            log.warn("Attempted to notify customer support for a null delivery.");
            return;
        }
        log.info("Notification: Delivery ID {} is overdue. Started at: {}. Please notify the customer support team.", delivery.getId(), delivery.getStartTime());
    }
}
