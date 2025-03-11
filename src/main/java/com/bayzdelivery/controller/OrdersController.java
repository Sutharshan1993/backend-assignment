package com.bayzdelivery.controller;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrdersController handles requests related to the Orders entity.
 * It provides endpoints to create, retrieve, and manage orders.
 * <p>
 * The controller maps its endpoints under "/orders" base path.
 * All methods log their entry, execution details, and exit points for better traceability.
 * <p>
 * - Dependencies:
 * Uses an instance of OrdersService to interact with the business logic and persistency layer.
 * <p>
 * - Endpoints:
 * 1. newOrder: Handles POST requests to create a new order.
 * 2. getAllOrders: Handles GET requests to retrieve all orders.
 * 3. getOrderById: Handles GET requests to retrieve a specific order by its ID.
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService orderService;

    /**
     * Creates a new order by persisting the provided order details.
     *
     * @param order the details of the order to be created
     * @return a ResponseEntity containing the created order's response object
     */
    @PostMapping("/newOrder")
    public ResponseEntity<OrderResponse> newOrder(@RequestBody Orders order) {
        log.info("Entered into create newOrder : {}", order);
        return ResponseEntity.ok(orderService.save(order));
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return a ResponseEntity containing a list of OrderResponse objects representing all available orders.
     */
    @GetMapping("/getAllOrder")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("Entered into getAllOrders");
        return ResponseEntity.ok(orderService.getAll());
    }

    /**
     * Retrieves the order details for the specified order ID.
     *
     * @param orderId the unique identifier of the order to retrieve
     * @return a ResponseEntity containing the order details if found,
     * or a 404 Not Found response if no order exists with the provided ID
     */
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
