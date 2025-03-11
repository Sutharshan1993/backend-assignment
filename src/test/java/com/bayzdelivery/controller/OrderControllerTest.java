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

@ExtendWith(MockitoExtension.class)
class OrdersControllerTest {

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