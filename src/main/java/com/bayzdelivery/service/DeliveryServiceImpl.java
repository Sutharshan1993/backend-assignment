package com.bayzdelivery.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.exceptions.DeliveryNotFoundException;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.OrdersRepository;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.utils.DeliveryHelper;
import com.bayzdelivery.utils.DeliveryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

  @Autowired
  DeliveryRepository deliveryRepository;

  @Autowired
  OrdersRepository ordersRepository;

  @Autowired
  PersonRepository personRepository;

    /**
     * Whenever a deliveryman Picks up delivery he creates a delivery entry with Status as Active or Picked up
     * checkOrder to verify the active order
     * checkDeliveryMan to verify the active Deliveryman
     * activeDeliveries checks the delivery man doesn't have multiple delivery
     * At the time we calculate the commission
     * @param delivery
     * @return
     */
  public DeliveryResponse createDelivery(Delivery delivery) {
      Delivery deliveryCreate;
   boolean checkOrder = ordersRepository.existsById(delivery.getOrders().getId());
    if(checkOrder){
      throw new IllegalArgumentException("Order does not exists");
    }
   boolean checkDeliveryMan = personRepository.existsById(delivery.getDeliveryMan().getId());
    if(checkDeliveryMan){
      throw new IllegalArgumentException("Delivery man does not exists");
    }
    int activeDeliveries = deliveryRepository.countActiveDeliveriesByDeliveryManId(delivery.getDeliveryMan().getId());
      if (activeDeliveries > 0) {
          throw new IllegalArgumentException("Delivery man is already delivering an order");
      }
      delivery.setStatus(DeliveryStatus.ACTIVE);
  double commission = calculateCommission(delivery.getOrders().getOrderPrice(), delivery.getDistance());
  delivery.setCommission(commission);
  deliveryCreate = deliveryRepository.save(delivery);
      return new DeliveryResponse(deliveryCreate.getId(),deliveryCreate.getDeliveryMan().getId(),deliveryCreate.getStartTime(),deliveryCreate.getStatus().toString());
  }
  private double calculateCommission(double orderPrice, double distance) {
    return  ((orderPrice * 0.05) + (distance * 0.5));
  }
  public DeliveryResponse completeDelivery(Delivery delivery) {
      Delivery deliveryCreate;
      double distance = delivery.getDistance();
      deliveryCreate = deliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));

        if (deliveryCreate.getStatus() == DeliveryStatus.COMPLETED) {
            throw new IllegalArgumentException("Delivery is already completed");
        }
      double commission = calculateCommission(deliveryCreate.getOrders().getOrderPrice(), distance);
      deliveryCreate.setCommission(commission);
      deliveryCreate.setEndTime(Instant.from(LocalDateTime.now()));
      deliveryCreate.setStatus(DeliveryStatus.COMPLETED);
      deliveryCreate = deliveryRepository.save(deliveryCreate);
      return new DeliveryResponse(deliveryCreate.getId(),deliveryCreate.getDeliveryMan().getId(),deliveryCreate.getStartTime(),deliveryCreate.getEndTime(),deliveryCreate.getStatus().toString(),deliveryCreate.getCommission(),deliveryCreate.getDistance());
    }
    public DeliveryResponse findById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .map(DeliveryHelper::mapToDeliveryResponse)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with ID: " + deliveryId));
    }

    public TopDeliveryMenResponse getTopDeliveryMen(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("StartTime must be before endTime");
        }

        List<Object[]> top3DeliveryMen = deliveryRepository.findTopDeliveryMenByCommission(startTime, endTime);

        // Calculate average commission
        List<DeliveryManCommission> topDeliveryMen = top3DeliveryMen.stream()
                .map(DeliveryHelper::mapToDeliveryManCommission)
                .toList();
        double totalCommissionTop3 = topDeliveryMen.stream().mapToDouble(DeliveryManCommission::totalCommission).sum();
        double averageCommissionOfTop3 = !topDeliveryMen.isEmpty() ? totalCommissionTop3 / topDeliveryMen.size() : 0.0;

        return new TopDeliveryMenResponse(topDeliveryMen, Math.max(averageCommissionOfTop3, 0.0));
    }


}
