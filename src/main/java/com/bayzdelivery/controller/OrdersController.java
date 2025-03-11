package com.bayzdelivery.controller;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService orderService;

    @PostMapping("/newOrder")
    public ResponseEntity<OrderResponse> newOrder(@RequestBody Orders order) {
        log.info("Entered into create newOrder : {}", order);
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping("/getAllOrder")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("Entered into getAllOrders");
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/getOrder/{order-id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(name = "order-id") Long orderId) {
        log.info("Entered into getOrderById for OrderId : {}", orderId);
        OrderResponse orders = orderService.findById(orderId);
        if ( orders != null ) {
            log.info("Exiting from getOrderById for orders : {}", orders);
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.notFound().build();
    }


}
