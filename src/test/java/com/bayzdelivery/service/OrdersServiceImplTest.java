package com.bayzdelivery.service;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.exceptions.OrderNotFoundException;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceImplTest {


    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private OrdersServiceImpl ordersService;

    private Orders order;
    private Person customer;

    @BeforeEach
    public void setUp() {
        customer = new Person();
        customer.setId(1L);
        customer.setName("John Doe");

        order = new Orders();
        order.setId(1L);
        order.setOrderName("Test Order");
        order.setOrderPrice(100.0);
        order.setCustomer(customer);
    }

    @Test
    public void testGetAll_Success() {
        when(ordersRepository.findAll()).thenReturn(List.of(order));
        List<OrderResponse> response = ordersService.getAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(order.getId(), response.get(0).id());
        assertEquals(order.getOrderName(), response.get(0).orderName());
        assertEquals(order.getOrderPrice(), response.get(0).orderPrice());
        assertEquals(order.getCustomer().getName(), response.get(0).customer());

        verify(ordersRepository, times(1)).findAll();
    }

    @Test
    public void testGetAll_NoOrders() {
        when(ordersRepository.findAll()).thenReturn(Collections.emptyList());
        List<OrderResponse> response = ordersService.getAll();
        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(ordersRepository, times(1)).findAll();
    }

    @Test
    public void testSave_Success() {
        when(ordersRepository.save(order)).thenReturn(order);
        OrderResponse response = ordersService.save(order);
        assertNotNull(response);
        assertEquals(order.getId(), response.id());
        assertEquals(order.getOrderName(), response.orderName());
        assertEquals(order.getOrderPrice(), response.orderPrice());
        assertEquals(order.getCustomer().getName(), response.customer());

        verify(ordersRepository, times(1)).save(order);
    }

    @Test
    public void testFindById_Success() {
        when(ordersRepository.findById(order.getId())).thenReturn(Optional.of(order));
        OrderResponse response = ordersService.findById(order.getId());
        assertNotNull(response);
        assertEquals(order.getId(), response.id());
        assertEquals(order.getOrderName(), response.orderName());
        assertEquals(order.getOrderPrice(), response.orderPrice());
        assertEquals(order.getCustomer().getName(), response.customer());

        verify(ordersRepository, times(1)).findById(order.getId());
    }

    @Test
    public void testFindById_OrderNotFound() {
        when(ordersRepository.findById(order.getId())).thenReturn(Optional.empty());
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            ordersService.findById(order.getId());
        });
        assertEquals("Order not found with ID: " + order.getId(), exception.getMessage());

        verify(ordersRepository, times(1)).findById(order.getId());
    }

    @Test
    public void testDeleteById_Success() {
        when(ordersRepository.findById(order.getId())).thenReturn(Optional.of(order));
        doNothing().when(ordersRepository).delete(order);
        ordersService.deleteById(order.getId());
        verify(ordersRepository, times(1)).findById(order.getId());
        verify(ordersRepository, times(1)).delete(order);
    }

    @Test
    public void testDeleteById_OrderNotFound() {
        when(ordersRepository.findById(order.getId())).thenReturn(Optional.empty());
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            ordersService.deleteById(order.getId());
        });
        assertEquals("Order not found with ID: " + order.getId(), exception.getMessage());

        verify(ordersRepository, times(1)).findById(order.getId());
        verify(ordersRepository, never()).delete(any());
    }

    @Test
    public void testUpdateOrder_Success() {
        Orders updatedOrder = new Orders();
        updatedOrder.setOrderName("Updated Order");
        updatedOrder.setOrderPrice(200.0);
        updatedOrder.setCustomer(customer);

        when(ordersRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(ordersRepository.save(order)).thenReturn(order);
        OrderResponse response = ordersService.updateOrder(order.getId(), updatedOrder);
        assertNotNull(response);
        assertEquals(order.getId(), response.id());
        assertEquals(updatedOrder.getOrderName(), response.orderName());
        assertEquals(updatedOrder.getOrderPrice(), response.orderPrice());
        assertEquals(updatedOrder.getCustomer().getName(), response.customer());

        verify(ordersRepository, times(1)).findById(order.getId());
        verify(ordersRepository, times(1)).save(order);
    }

    @Test
    public void testUpdateOrder_OrderNotFound() {
        Orders updatedOrder = new Orders();
        updatedOrder.setOrderName("Updated Order");
        updatedOrder.setOrderPrice(200.0);
        updatedOrder.setCustomer(customer);

        when(ordersRepository.findById(order.getId())).thenReturn(Optional.empty());
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            ordersService.updateOrder(order.getId(), updatedOrder);
        });
        assertEquals("Order not found with ID: " + order.getId(), exception.getMessage());

        verify(ordersRepository, times(1)).findById(order.getId());
        verify(ordersRepository, never()).save(any());
    }
}
