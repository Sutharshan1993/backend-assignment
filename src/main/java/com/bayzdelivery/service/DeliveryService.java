package com.bayzdelivery.service;

import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;

import java.time.LocalDateTime;

public interface DeliveryService {

  DeliveryResponse createDelivery(Delivery delivery);
  DeliveryResponse completeDelivery(Delivery delivery);
  DeliveryResponse findById(Long deliveryId);
  TopDeliveryMenResponse getTopDeliveryMen(LocalDateTime startTime, LocalDateTime endTime);
}
