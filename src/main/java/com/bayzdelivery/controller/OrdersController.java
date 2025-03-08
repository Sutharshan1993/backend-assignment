package com.bayzdelivery.controller;

import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.service.OrdersService;
import com.bayzdelivery.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {


        @Autowired
        OrdersService orderService;

        @PostMapping("/newOrder")
        public ResponseEntity<Orders> register(@RequestBody Orders p) {
        return ResponseEntity.ok(orderService.save(p));
    }

        @GetMapping("/getAllOrder")
        public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

        @GetMapping("/getOrder/{order-id}")
        public ResponseEntity<Orders> getOrderById(@PathVariable(name="order-id", required=true)Long orderId) {
        Orders orders = orderService.findById(orderId);
        if (orders != null) {
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.notFound().build();
    }


}
