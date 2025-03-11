package com.bayzdelivery.service;

import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;

import java.time.LocalDateTime;

/**
 * DeliveryService interface provides operations to handle the lifecycle of deliveries
 * and retrieve performance metrics related to delivery personnel.
 * <p>
 * Features:
 * - Create and manage deliveries.
 * - Fetch details of specific deliveries by ID.
 * - Mark deliveries as complete and calculate related metrics such as distance.
 * - Retrieve performance data of top delivery personnel within a specified time range.
 */
public interface DeliveryService {

    DeliveryResponse createDelivery(Delivery delivery);

    DeliveryResponse completeDelivery(double distance, Long deliveryId);

    DeliveryResponse findById(Long deliveryId);

    TopDeliveryMenResponse getTopDeliveryMen(LocalDateTime startTime, LocalDateTime endTime);
}
