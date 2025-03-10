package com.bayzdelivery.controller;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService orderService;

    @PostMapping("/newOrder")
    public ResponseEntity<OrderResponse> newOrder(@RequestBody Orders order) {
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping("/getAllOrder")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/getOrder/{order-id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(name = "order-id") Long orderId) {
        OrderResponse orders = orderService.findById(orderId);
        if ( orders != null ) {
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.notFound().build();
    }


}
