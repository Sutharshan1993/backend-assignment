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

/**
 * Implementation class for the OrdersService interface.
 * <p>
 * This class provides concrete implementations for managing orders in the system.
 * It interacts with the OrdersRepository and employs helper methods for mapping
 * entity objects to response data transfer objects (DTOs). The service is marked
 * with @Service annotation to indicate its role in the Spring service layer and
 * uses Lombok annotations for logging and dependency injection.
 * <p>
 * Responsibilities include:
 * - Fetching all orders from the repository and mapping them to response DTOs.
 * - Saving a new order to
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;

    /**
     * Retrieves all orders from the repository, maps them to OrderResponse DTO objects,
     * and returns the resulting list.
     *
     * @return a list of OrderResponse objects representing all orders in the repository
     */
    @Override
    public List<OrderResponse> getAll() {
        log.info("Entered into getAll Orders");
        return ordersRepository.findAll()
                .stream()
                .map(OrderHelper::mapToOrdersResponse)
                .toList();
    }

    /**
     * Saves a new order to the repository and maps the saved order to an OrderResponse DTO.
     *
     * @param ord the order entity to be saved
     * @return an OrderResponse object representing the saved order
     */
    @Override
    public OrderResponse save(Orders ord) {
        log.info("Entered into save Orders");
        Orders savedOrder = ordersRepository.save(ord);
        log.info("Exiting from save Order:{}", savedOrder);
        return OrderHelper.mapToOrdersResponse(savedOrder);
    }

    /**
     * Retrieves an order by its unique identifier and maps it to an OrderResponse DTO.
     * If the order is not found, throws an OrderNotFoundException.
     *
     * @param orderId the unique identifier of the order to be retrieved
     * @return an OrderResponse object representing the order
     * @throws OrderNotFoundException if no order is found with the given ID
     */
    @Override
    public OrderResponse findById(Long orderId) {
        log.info("Entered into findById Orders of OrderId :{}", orderId);
        return ordersRepository.findById(orderId).map(orders -> {
                    log.info("Found order and Exiting: {}", orders);
                    return OrderHelper.mapToOrdersResponse(orders);
                })
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

    }

    /**
     * Deletes an order from the repository by its unique identifier.
     * If the order is not found, throws an OrderNotFoundException.
     *
     * @param orderId the unique identifier of the order to be deleted
     * @throws OrderNotFoundException if no order is found with the given ID
     */
    @Override
    public void deleteById(Long orderId) {
        log.info("Entered into deleteById Orders of OrderId :{}", orderId);
        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        ordersRepository.delete(order);
        log.info("Exiting from deleteById Orders of OrderId :{}", orderId);
    }

    /**
     * Updates the details of an existing order.
     * If the order with the specified ID does not exist, an exception is thrown.
     *
     * @param orderId      the unique identifier of the order to be updated
     * @param updatedOrder the object containing updated order details
     * @return an OrderResponse object representing the updated order
     * @throws OrderNotFoundException if no order is found with the given ID
     */
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
