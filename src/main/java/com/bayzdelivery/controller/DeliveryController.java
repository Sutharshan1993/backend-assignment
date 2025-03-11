package com.bayzdelivery.controller;

import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.service.DeliveryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * DeliveryController handles delivery-related operations such as creating, completing,
 * retrieving deliveries, and fetching top-performing delivery personnel.
 * It acts as a REST API entry point for managing deliveries.
 */
@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * Creates a new delivery based on the provided delivery details.
     *
     * @param delivery the delivery details to be created; must be valid and not null
     * @return a {@link ResponseEntity} containing the created {@link DeliveryResponse}
     */
    @PostMapping(path = "/createDelivery")
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody @Valid Delivery delivery) {
        log.info("Entered into created Delivery : {}", delivery);
        DeliveryResponse createDelivery = deliveryService.createDelivery(delivery);
        log.info("Exiting from created Delivery : {}", createDelivery);
        return ResponseEntity.ok(createDelivery);
    }

    /**
     * Completes the delivery process for a given delivery ID and records the distance traveled.
     *
     * @param distance   the distance covered for this delivery
     * @param deliveryId the ID of the delivery to be completed; must not be null
     * @return a {@link ResponseEntity} containing the details of the completed {@link DeliveryResponse}
     */
    @PostMapping(path = "/completeDelivery/{distance}/{delivery-id}")
    public ResponseEntity<DeliveryResponse> completeDelivery(@PathVariable double distance, @PathVariable(name = "delivery-id") @NotNull Long deliveryId) {
        log.info("Entered into complete Delivery for Delivery ID: {}", deliveryId);
        DeliveryResponse completedDelivery = deliveryService.completeDelivery(distance, deliveryId);
        log.info("Exiting from complete Delivery for Delivery ID: {}", completedDelivery);
        return ResponseEntity.ok(completedDelivery);
    }

    /**
     * Retrieves the delivery details for a given delivery ID.
     *
     * @param deliveryId the ID of the delivery to be retrieved; must not be null
     * @return a {@link ResponseEntity} containing the {@link DeliveryResponse} if the delivery exists,
     * or a {@link ResponseEntity} with a 404 Not Found status if the delivery is not found
     */
    @GetMapping(path = "/getDelivery/{delivery-id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable(name = "delivery-id") @NotNull Long deliveryId) {
        log.info("Entered into getDeliveryById Delivery ID: {}", deliveryId);
        DeliveryResponse delivery = deliveryService.findById(deliveryId);
        if ( delivery != null ) {
            log.info("Exiting from getDeliveryById for Delivery ID: {}", delivery);
            return ResponseEntity.ok(delivery);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves the top-performing delivery men within a specified time range.
     *
     * @param startTime the start of the time range, formatted in ISO date-time; must not be null
     * @param endTime   the end of the time range, formatted in ISO date-time; must not be null
     * @return a {@link ResponseEntity} containing the {@link TopDeliveryMenResponse} with the details of top-performing delivery men
     */
    @GetMapping("/top-delivery-men")
    public ResponseEntity<TopDeliveryMenResponse> getTopDeliveryMen(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        log.info("Entered into getTopDeliveryMen StartTime : {},EndTime : {}", startTime, endTime);
        var response = deliveryService.getTopDeliveryMen(startTime, endTime);
        log.info("Exiting from getTopDeliveryMen StartTime : {},EndTime : {}", startTime, endTime);
        return ResponseEntity.ok(response);
    }
}
