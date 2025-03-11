package com.bayzdelivery.service;

/**
 * Service interface for checking delivery statuses.
 * This service is responsible for managing the logic that ensures the timely monitoring
 * of ongoing deliveries and taking action on overdue deliveries.
 * <p>
 * Methods in this service typically involve:
 * - Identifying overdue deliveries based on predefined thresholds.
 * - Notifying customer support for deliveries that have exceeded their allowed duration.
 * - Ensuring a scheduled execution to consistently check delivery statuses.
 */
public interface DeliveryCheckService {

    void checkDelivery();
}
