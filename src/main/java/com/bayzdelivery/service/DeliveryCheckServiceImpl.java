package com.bayzdelivery.service;

import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.utils.DeliveryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class DeliveryCheckServiceImpl implements DeliveryCheckService {
    @Autowired
    DeliveryRepository deliveryRepository;
    public DeliveryCheckServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    @Async
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void checkDelivery() {
        var now = LocalDateTime.now();
        var overdueThreshold = now.minusMinutes(45).atZone(ZoneId.systemDefault()).toInstant();
       // var overdueLocalDateTime = LocalDateTime.ofInstant(overdueThreshold, ZoneId.systemDefault());
        var overdueDeliveries = deliveryRepository.findByStatusAndStartTimeBefore(
                DeliveryStatus.ACTIVE, overdueThreshold);
        if (!overdueDeliveries.isEmpty()) {
            overdueDeliveries.forEach(delivery -> {
            System.out.println("Notification: Delivery ID " + delivery.getId() +
                    " is overdue. Started at: " + delivery.getStartTime() +
                    ". Please notify the customer support team.");
        });

        }

    }
}
