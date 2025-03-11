package com.bayzdelivery.service;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.model.Orders;

import java.util.List;

/**
 * OrdersService interface defines the operations available for managing
 * customer orders in the system.
 * <p>
 * This service provides functionality for:
 * <p>
 * - Retrieving all orders.
 * - Persisting a new order.
 * - Fetching a specific order by its unique identifier.
 * - Deleting an order using its ID.
 * - Updating the details of an existing order.
 */
public interface OrdersService {
    List<OrderResponse> getAll();

    OrderResponse save(Orders p);

    OrderResponse findById(Long orderId);

    void deleteById(Long orderId);

    OrderResponse updateOrder(Long orderId, Orders updatedOrder);
}
