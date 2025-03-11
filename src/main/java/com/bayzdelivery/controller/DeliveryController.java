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

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping(path = "/createDelivery")
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody @Valid Delivery delivery) {
        log.info("Entered into created Delivery : {}", delivery);
        DeliveryResponse createDelivery = deliveryService.createDelivery(delivery);
        log.info("Exiting from created Delivery : {}", createDelivery);
        return ResponseEntity.ok(createDelivery);
    }

    @PostMapping(path = "/completeDelivery/{distance}/{delivery-id}")
    public ResponseEntity<DeliveryResponse> completeDelivery(@PathVariable double distance, @PathVariable(name = "delivery-id") @NotNull Long deliveryId) {
        log.info("Entered into complete Delivery for Delivery ID: {}", deliveryId);
        DeliveryResponse completedDelivery = deliveryService.completeDelivery(distance, deliveryId);
        log.info("Exiting from complete Delivery for Delivery ID: {}", completedDelivery);
        return ResponseEntity.ok(completedDelivery);
    }

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
