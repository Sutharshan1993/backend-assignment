package com.bayzdelivery.service;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.exceptions.OrderNotFoundException;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.repositories.OrdersRepository;
import com.bayzdelivery.utils.OrderHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    @Override
    public List<OrderResponse> getAll() {
        log.info("Entered into getAll Orders");
        return ordersRepository.findAll()
                .stream()
                .map(OrderHelper::mapToOrdersResponse)
                .toList();
    }

    @Override
    public OrderResponse save(Orders ord) {
        log.info("Entered into save Orders");
        Orders savedOrder = ordersRepository.save(ord);
        log.info("Exiting from save Order:{}", savedOrder);
        return OrderHelper.mapToOrdersResponse(savedOrder);
    }

    @Override
    public OrderResponse findById(Long orderId) {
        log.info("Entered into findById Orders of OrderId :{}", orderId);
        return ordersRepository.findById(orderId).map(orders -> {
                    log.info("Found order and Exiting: {}", orders);
                    return OrderHelper.mapToOrdersResponse(orders);
                })
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

    }

    @Override
    public void deleteById(Long orderId) {
        log.info("Entered into deleteById Orders of OrderId :{}", orderId);
        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        ordersRepository.delete(order);
        log.info("Exiting from deleteById Orders of OrderId :{}", orderId);
    }

    @Override
    public OrderResponse updateOrder(Long orderId, Orders updatedOrder) {
        log.info("Entered into updateOrder Orders of OrderId :{}", orderId);
        Orders existingOrder = ordersRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        existingOrder.setOrderName(updatedOrder.getOrderName());
        existingOrder.setOrderPrice(updatedOrder.getOrderPrice());
        existingOrder.setCustomer(updatedOrder.getCustomer());

        Orders savedOrder = ordersRepository.save(existingOrder);
        log.info("Exiting from updateOrder Orders of OrderId :{}", orderId);
        return OrderHelper.mapToOrdersResponse(savedOrder);
    }


}
