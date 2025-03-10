package com.bayzdelivery.service;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.utils.DeliveryStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
@Service
public class DeliveryCheckServiceImpl implements DeliveryCheckService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryCheckServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    @Scheduled(fixedRate = 300_000)
    public void checkDelivery() {
        var now = LocalDateTime.now();
        var overdueThreshold = LocalDateTime.now().minusMinutes(45);
        ;
        var overdueDeliveries = deliveryRepository.findByStatusAndStartTimeBefore(
                DeliveryStatus.ACTIVE, Instant.from(overdueThreshold));
        if ( !overdueDeliveries.isEmpty() ) {
            overdueDeliveries.forEach(this::notifyCustomerSupport);
        }

    }

    private void notifyCustomerSupport(Delivery delivery) {
        log.info("Notification: Delivery ID {} is overdue. Started at: {}. Please notify the customer support team.", delivery.getId(), delivery.getStartTime());
    }
}
