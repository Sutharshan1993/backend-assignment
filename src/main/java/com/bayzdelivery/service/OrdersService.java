package com.bayzdelivery.service;

import com.bayzdelivery.model.Orders;


import java.util.List;

public interface OrdersService {
    List<Orders> getAll();

    Orders save(Orders p);

    Orders findById(Long orderId);

    Orders deleteById(Long orderId);

    Orders updateOrder(Long orderId);
}
