package com.bayzdelivery.controller;

import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.service.DeliveryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping(path = "/createDelivery")
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody @Valid Delivery delivery) {
        DeliveryResponse createDelivery = deliveryService.createDelivery(delivery);
        return ResponseEntity.ok(createDelivery);
    }

    @PostMapping(path = "/completeDelivery/{distance}/{delivery-id}")
    public ResponseEntity<DeliveryResponse> completeDelivery(@PathVariable double distance, @PathVariable(name = "delivery-id") @NotNull Long deliveryId) {
        DeliveryResponse completedDelivery = deliveryService.completeDelivery(distance, deliveryId);
        return ResponseEntity.ok(completedDelivery);
    }

    @GetMapping(path = "/getDelivery/{delivery-id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable(name = "delivery-id") @NotNull Long deliveryId) {
        DeliveryResponse delivery = deliveryService.findById(deliveryId);
        if ( delivery != null )
            return ResponseEntity.ok(delivery);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/top-delivery-men")
    public ResponseEntity<TopDeliveryMenResponse> getTopDeliveryMen(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ) {
        var response = deliveryService.getTopDeliveryMen(startTime, endTime);
        return ResponseEntity.ok(response);
    }
}
