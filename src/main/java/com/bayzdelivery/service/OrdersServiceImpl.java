package com.bayzdelivery.service;

import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService{

    @Autowired
    OrdersRepository ordersRepository;
    @Override
    public List<Orders> getAll() {
        List<Orders> ordersList = new ArrayList<>();
        ordersRepository.findAll().forEach(ordersList::add);
        return ordersList;
    }

    @Override
    public Orders save(Orders ord) {
        return ordersRepository.save(ord);
    }

    @Override
    public Orders findById(Long orderId) {
        Optional<Orders> dbOrders = ordersRepository.findById(orderId);
        return dbOrders.orElse(null);
    }

    @Override
    public Orders deleteById(Long orderId) {
        return null;
    }

    @Override
    public Orders updateOrder(Long orderId) {
        return null;
    }
}
