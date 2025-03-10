package com.bayzdelivery.controller;

import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bayzdelivery.service.DeliveryService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

  @PostMapping(path ="/createDelivery")
  public ResponseEntity<DeliveryResponse> createNewDelivery(@RequestBody Delivery delivery) {
    DeliveryResponse createDelivery = deliveryService.createDelivery(delivery);
    return ResponseEntity.ok(createDelivery);
  }

  @PostMapping(path ="/completeDelivery")
  public ResponseEntity<DeliveryResponse> completeDelivery(@RequestBody Delivery delivery) {
    DeliveryResponse completedDelivery = deliveryService.completeDelivery(delivery);
    return ResponseEntity.ok(completedDelivery);
  }

  @GetMapping(path = "/getDelivery/{delivery-id}")
  public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable(name="delivery-id",required=true)Long deliveryId){
    DeliveryResponse delivery = deliveryService.findById(deliveryId);
    if (delivery !=null)
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
