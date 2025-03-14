package com.bayzdelivery.jobs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for handling notifications related to delayed deliveries.
 * It contains methods for periodic checks and notifying the customer support team
 * in case of delayed deliveries.
 */
@Component
public class DelayedDeliveryNotifier {

    private static final Logger LOG = LoggerFactory.getLogger(DelayedDeliveryNotifier.class);

    /**
     * Use this method for the TASK 3
     */
    @Scheduled(fixedDelay = 30000)
    public void checkDelayedDeliveries() {

    }


    /**
     * This method should be called to notify customer support team
     * It just writes notification on console but it may be email or push notification in real.
     * So that this method should run in an async way.
     */
    @Async
    public void notifyCustomerSupport() {
        LOG.info("Customer support team is notified!");
    }
}
