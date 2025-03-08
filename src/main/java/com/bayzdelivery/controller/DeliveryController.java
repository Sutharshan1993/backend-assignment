package com.bayzdelivery.controller;

import com.bayzdelivery.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bayzdelivery.service.DeliveryService;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

  @PostMapping(path ="/createDelivery")
  public ResponseEntity<Delivery> createNewDelivery(@RequestBody Delivery delivery) {
    return ResponseEntity.ok(deliveryService.save(delivery));
  }

  @GetMapping(path = "/getDelivery/{delivery-id}")
  public ResponseEntity<Delivery> getDeliveryById(@PathVariable(name="delivery-id",required=true)Long deliveryId){
    Delivery delivery = deliveryService.findById(deliveryId);
    if (delivery !=null)
      return ResponseEntity.ok(delivery);
    return ResponseEntity.notFound().build();
  }
}
