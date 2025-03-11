package com.bayzdelivery.controller;

import com.bayzdelivery.dto.OrderResponse;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.service.OrdersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * OrderControllerTest is a test class that verifies the behavior of the OrdersController
 * by mocking the OrdersService dependency and using a standalone MockMvc setup.
 * <p>
 * This class primarily tests the controller layer to ensure that endpoints behave
 * as expected under various scenarios.
 * <p>
 * Responsibilities:
 * - Verifying successful creation of a new order via the "/newOrder" endpoint.
 * - Ensuring that all orders are retrieved correctly through the "/getAllOrder" endpoint.
 * - Testing the retrieval of a specific order by ID through the "/getOrder/{id}" endpoint.
 * - Confirming appropriate responses when trying to retrieve a non-existent order by ID.
 * <p>
 * Key Annotations:
 * - @ExtendWith(MockitoExtension.class): Integrates Mockito with JUnit for dependency mocking.
 * - @Mock: Creates mock dependencies of the OrdersService.
 * - @InjectMocks: Injects mocks into the OrdersController instance under test.
 * <p>
 * Test Methods:
 * 1. newOrder_shouldReturnOkAndOrderResponse:
 * Tests the order creation process and asserts that the response contains the correct data.
 * <p>
 * 2. getAllOrders_shouldReturnOkAndListOfOrderResponses:
 * Tests retrieval of all orders and validates the response structure and content.
 * <p>
 * 3. getOrderById_shouldReturnOkAndOrderResponse_whenOrderExists:
 * Validates that the correct response is returned when a valid order ID is specified.
 * <p>
 * 4. getOrderById_shouldReturnNotFound_whenOrderDoesNotExist:
 * Ensures that a 404 status is returned when a nonexistent order ID is requested.
 */
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrdersService orderService;

    @InjectMocks
    private OrdersController ordersController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ordersController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void newOrder_shouldReturnOkAndOrderResponse() throws Exception {
        Orders order = new Orders();
        order.setId(1L);
        OrderResponse orderResponse = new OrderResponse(1L, "TestOrder", 100.0, "COMPLETED");

        when(orderService.save(any(Orders.class))).thenReturn(orderResponse);

        mockMvc.perform(post("/orders/newOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(orderService, times(1)).save(any(Orders.class));
    }

    @Test
    void getAllOrders_shouldReturnOkAndListOfOrderResponses() throws Exception {
        OrderResponse orderResponse1 = new OrderResponse(1L, "TestOrder", 100.0, "Assessment");

        OrderResponse orderResponse2 = new OrderResponse(2L, "TestOrder1", 1020.0, "Susan");

        List<OrderResponse> orderResponses = Arrays.asList(orderResponse1, orderResponse2);

        when(orderService.getAll()).thenReturn(orderResponses);

        mockMvc.perform(get("/orders/getAllOrder"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(orderService, times(1)).getAll();
    }

    @Test
    void getOrderById_shouldReturnOkAndOrderResponse_whenOrderExists() throws Exception {
        OrderResponse orderResponse = new OrderResponse(1L, "TestOrder", 100.0, "COMPLETED");

        when(orderService.findById(1L)).thenReturn(orderResponse);

        mockMvc.perform(get("/orders/getOrder/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(orderService, times(1)).findById(1L);
    }

    @Test
    void getOrderById_shouldReturnNotFound_whenOrderDoesNotExist() throws Exception {
        when(orderService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/orders/getOrder/1"))
                .andExpect(status().isNotFound());

        verify(orderService, times(1)).findById(1L);
    }
}