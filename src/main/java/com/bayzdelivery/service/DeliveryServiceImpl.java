package com.bayzdelivery.service;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.exceptions.DeliveryNotFoundException;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.repositories.OrdersRepository;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.utils.DeliveryHelper;
import com.bayzdelivery.utils.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrdersRepository ordersRepository;
    private final PersonRepository personRepository;


    /**
     * Whenever a deliveryman Picks up delivery he creates a delivery entry with Status as Active or Picked up
     * checkOrder to verify the active order
     * checkDeliveryMan to verify the active Deliveryman
     * activeDeliveries checks the delivery man doesn't have multiple delivery
     * At the time we calculate the commission
     *
     * @param delivery
     * @return
     */
    public DeliveryResponse createDelivery(Delivery delivery) {


        if ( !ordersRepository.existsById(delivery.getOrders().getId()) ) {
            throw new IllegalArgumentException("Order does not exists");
        }

        if ( !personRepository.existsById(delivery.getDeliveryMan().getId()) ) {
            throw new IllegalArgumentException("Delivery man does not exists");
        }
        int activeDeliveries = deliveryRepository.countByDeliveryManIdAndStatus(delivery.getDeliveryMan().getId(), DeliveryStatus.ACTIVE);
        if ( activeDeliveries > 0 ) {
            throw new IllegalArgumentException("Delivery man is already delivering an order");
        }
        delivery.setStatus(DeliveryStatus.ACTIVE);

        delivery.setCommission(calculateCommission(delivery.getOrders().getOrderPrice(), delivery.getDistance()));
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return new DeliveryResponse(savedDelivery.getId(), savedDelivery.getDeliveryMan().getId(), savedDelivery.getStartTime(), savedDelivery.getStatus().toString());
    }

    private double calculateCommission(double orderPrice, double distance) {
        return ((orderPrice * 0.05) + (distance * 0.5));
    }

    /**
     * @param distance
     * @param deliveryId
     * @return
     */
    public DeliveryResponse completeDelivery(double distance, Long deliveryId) {
        Delivery deliveryCreate = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found"));

        if ( deliveryCreate.getStatus() == DeliveryStatus.COMPLETED ) {
            throw new IllegalStateException("Delivery is already completed");
        }

        deliveryCreate.setCommission(calculateCommission(deliveryCreate.getOrders().getOrderPrice(), distance));
        deliveryCreate.setEndTime(Instant.now());
        deliveryCreate.setStatus(DeliveryStatus.COMPLETED);
        Delivery updatedDelivery = deliveryRepository.save(deliveryCreate);
        return new DeliveryResponse(updatedDelivery.getId(), updatedDelivery.getDeliveryMan().getId(), updatedDelivery.getStartTime(), updatedDelivery.getEndTime(), updatedDelivery.getStatus().toString(), updatedDelivery.getCommission(), updatedDelivery.getDistance());
    }

    /**
     * @param deliveryId
     * @return
     */
    public DeliveryResponse findById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .map(DeliveryHelper::mapToDeliveryResponse)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with ID: " + deliveryId));
    }

    /**
     * Returns the top 3 delivery men with the highest commission.
     */
    public TopDeliveryMenResponse getTopDeliveryMen(LocalDateTime startTime, LocalDateTime endTime) {
        if ( startTime.isAfter(endTime) ) {
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
