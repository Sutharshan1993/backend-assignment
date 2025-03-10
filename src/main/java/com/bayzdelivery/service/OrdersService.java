package com.bayzdelivery.service;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.model.Orders;

import java.util.List;

public interface OrdersService {
    List<OrderResponse> getAll();

    OrderResponse save(Orders p);

    OrderResponse findById(Long orderId);

    void deleteById(Long orderId);

    OrderResponse updateOrder(Long orderId, Orders updatedOrder);
}
