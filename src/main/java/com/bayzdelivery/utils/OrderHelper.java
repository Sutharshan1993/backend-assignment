package com.bayzdelivery.utils;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.model.Orders;

/**
 * Utility class for handling order-related data transformations.
 * <p>
 * This class contains static utility methods to map an `Orders` entity
 * to its corresponding `OrderResponse` DTO. The class is intended to be
 * non-instantiable and should only be utilized for its static methods.
 */
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
