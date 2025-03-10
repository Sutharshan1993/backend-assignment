package com.bayzdelivery.service;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.exceptions.OrderNotFoundException;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.repositories.OrdersRepository;
import com.bayzdelivery.utils.OrderHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    @Override
    public List<OrderResponse> getAll() {
        return ordersRepository.findAll()
                .stream()
                .map(OrderHelper::mapToOrdersResponse)
                .toList();
    }

    @Override
    public OrderResponse save(Orders ord) {
        Orders savedOrder = ordersRepository.save(ord);
        return OrderHelper.mapToOrdersResponse(savedOrder);
    }

    @Override
    public OrderResponse findById(Long orderId) {
        return ordersRepository.findById(orderId).map(OrderHelper::mapToOrdersResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

    }

    @Override
    public void deleteById(Long orderId) {
        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        ordersRepository.delete(order);
    }

    @Override
    public OrderResponse updateOrder(Long orderId, Orders updatedOrder) {
        Orders existingOrder = ordersRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        existingOrder.setOrderName(updatedOrder.getOrderName());
        existingOrder.setOrderPrice(updatedOrder.getOrderPrice());
        existingOrder.setCustomer(updatedOrder.getCustomer());

        Orders savedOrder = ordersRepository.save(existingOrder);
        return OrderHelper.mapToOrdersResponse(savedOrder);
    }


}
