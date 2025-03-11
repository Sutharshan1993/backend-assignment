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

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryCheckServiceImpl implements DeliveryCheckService {

    private final DeliveryRepository deliveryRepository;

    @Value("${delivery-check.overdue-threshold-minutes}")
    private int overdueThresholdMinutes;

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

    void notifyCustomerSupport(Delivery delivery) {
        if ( delivery == null ) {
            log.warn("Attempted to notify customer support for a null delivery.");
            return;
        }
        log.info("Notification: Delivery ID {} is overdue. Started at: {}. Please notify the customer support team.", delivery.getId(), delivery.getStartTime());
    }
}
