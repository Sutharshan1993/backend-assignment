package com.bayzdelivery.service;

import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;

import java.util.List;

public interface OrdersService {
    public List<Orders> getAll();

    public Orders save(Orders p);

    public Orders findById(Long orderId);

    public Orders deleteById(Long orderId);

    public Orders updateOrder(Long orderId);
}
