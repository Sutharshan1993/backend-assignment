package com.bayzdelivery.controller;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.service.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DeliveryControllerTest {

    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryController deliveryController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(deliveryController).build();
    }

  /*  @Test
    public void testCreateDelivery() throws Exception {
       *//* Orders mockOrders = new Orders();
        mockOrders.setId(1L);
        mockOrders.setOrderPrice(100.0);
        Person mockDeliveryMan = new Person();
        mockDeliveryMan.setId(123L);
        Person customer = new Person();
        customer.setName("<NAME>");
        mockDeliveryMan.setName("<NAME>");
        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(1L);
        mockDelivery.setStatus(DeliveryStatus.ACTIVE);
        mockDelivery.setStartTime(Instant.now());
        mockDelivery.setDistance(10.0);
        mockDelivery.setCustomer(customer);
        mockDelivery.setOrders(mockOrders);
        mockDelivery.setDeliveryMan(mockDeliveryMan);
        *//*
        String deliveryJson = """
                {
                   "id": 1,
                   "deliveryMan": {
                     "id": 1
                   },
                   "customer": {
                     "id": 2
                   },
                   "order": {
                     "id": 1,
                     "price": 100.0
                   },
                   "distance": 10.0,
                   "startTime": "2023-10-01T10:00:00",
                   "status": "ACTIVE"
                 }
                
                """;

        DeliveryResponse mockResponse = new DeliveryResponse(1L, 123L, Instant.now(), "CREATED");

        when(deliveryService.createDelivery(any(Delivery.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/delivery/createDelivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"status\": \"CREATED\"}").content(deliveryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("CREATED"));
    }*/

    @Test
    public void testCompleteDelivery() throws Exception {
        DeliveryResponse mockResponse = new DeliveryResponse(1L, 123L, Instant.now(), "COMPLETED");

        when(deliveryService.completeDelivery(anyDouble(), anyLong())).thenReturn(mockResponse);
        String deliveryJson = """
                {
                    "startTime": "2023-10-01T10:00:00Z",
                    "endTime": "2023-10-01T12:00:00Z",
                    "status": "CREATED"
                }
                """;
        mockMvc.perform(post("/delivery/completeDelivery/10.5/1")
                        .contentType(MediaType.APPLICATION_JSON).content(deliveryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    public void testGetDeliveryById() throws Exception {
        DeliveryResponse mockResponse = new DeliveryResponse(1L, 123L, Instant.now(), "DELIVERED");

        when(deliveryService.findById(anyLong())).thenReturn(mockResponse);

        mockMvc.perform(get("/delivery/getDelivery/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("DELIVERED"));
    }

    @Test
    public void testGetDeliveryById_NotFound() throws Exception {
        when(deliveryService.findById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/delivery/getDelivery/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTopDeliveryMen() throws Exception {
        List<DeliveryManCommission> top3DeliveryMen = List.of(
                new DeliveryManCommission(1L, "John Doe", 100.0, 5L, 100.0),
                new DeliveryManCommission(2L, "Jane Doe", 200.0, 10L, 10.0)
        );
        TopDeliveryMenResponse mockResponse = new TopDeliveryMenResponse(top3DeliveryMen, 150.0);


        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1);

        when(deliveryService.getTopDeliveryMen(startTime, endTime)).thenReturn(mockResponse);

        mockMvc.perform(get("/delivery/top-delivery-men")
                        .param("startTime", startTime.toString())
                        .param("endTime", endTime.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topDeliveryMen").isArray());
    }

    @Test
    public void testCreateDelivery_InvalidInput() throws Exception {
        mockMvc.perform(post("/delivery/createDelivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Empty or invalid JSON
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCompleteDelivery_InvalidInput() throws Exception {
        mockMvc.perform(post("/delivery/completeDelivery/invalid/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetDeliveryById_InvalidInput() throws Exception {
        mockMvc.perform(get("/delivery/getDelivery/invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTopDeliveryMen_InvalidInput() throws Exception {
        mockMvc.perform(get("/delivery/top-delivery-men")
                        .param("startTime", "invalid")
                        .param("endTime", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}