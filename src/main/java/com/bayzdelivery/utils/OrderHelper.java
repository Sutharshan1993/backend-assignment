package com.bayzdelivery.utils;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.model.Orders;

public class OrderHelper {
    private OrderHelper() {
    }

    public static OrderResponse mapToOrdersResponse(Orders orders) {

        return new OrderResponse(
                orders.getId(),
                orders.getOrderName(),
                orders.getOrderPrice(),
                orders.getCustomer().getName()
        );
    }
}
