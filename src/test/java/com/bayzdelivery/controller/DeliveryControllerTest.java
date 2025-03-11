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

/**
 * Unit test class for DeliveryController that performs testing of REST endpoints related to delivery operations.
 * The class uses MockMvc for simulating HTTP requests and validating the behavior of the DeliveryController.
 * Dependencies of the controller are mocked to isolate and test the controller's logic.
 * <p>
 * Test methods include:
 * - Testing successful and error scenarios for creating a delivery.
 * - Testing completion of deliveries with valid and invalid input data.
 * - Fetching delivery details by ID, including both valid requests and error conditions like "not found".
 * - Retrieving the top-performing delivery personnel within a time range, including both correct input and bad requests.
 * <p>
 * MockMvc is initialized in the setup method to configure standalone testing of DeliveryController.
 * Mocks for DeliveryService are configured using Mockito to simulate its behavior during tests.
 */
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

    /**
     * Tests the functionality of completing a delivery through the DeliveryController.
     * <p>
     * This test verifies the successful completion of a delivery by mocking the service response,
     * sending a request to the endpoint, and asserting the correctness of the HTTP response and
     * the returned JSON structure.
     * <p>
     * Steps:
     * 1. Mocks the response of the deliveryService's `completeDelivery` method with predefined values.
     * 2. Constructs a delivery JSON payload to simulate request body data for the delivery.
     * 3. Sends a POST request to the `/delivery/completeDelivery/{distance}/{deliveryId}` endpoint with the JSON payload.
     * 4. Verifies that the response HTTP status is 200 (OK).
     * 5. Asserts the response JSON contains expected values, including "id" and "status".
     * <p>
     * Dependencies:
     * - MockMvc framework for simulating web requests and responses.
     * - Mockito for service mocking.
     * - Spring's `MediaType` for content type handling.
     * - JSONPath for the validation of JSON response content.
     * <p>
     * Exceptions:
     * - Throws any exceptions encountered during the test execution.
     */

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

    /**
     * Tests the functionality of retrieving a delivery by its ID through the DeliveryController.
     * <p>
     * This test verifies that the `GET /delivery/getDelivery/{id}` endpoint retrieves the
     * correct delivery details when a valid delivery ID is provided.
     * <p>
     * Steps:
     * 1. Mocks the `deliveryService.findById` method to return a predefined DeliveryResponse object.
     * 2. Sends a GET request to the `/delivery/getDelivery/{id}` endpoint with a valid delivery ID.
     * 3. Asserts that the HTTP status of the response is 200 (OK).
     * 4. Verifies that the JSON response includes the expected delivery data, such as:
     * - The "id" field matches the mock delivery ID.
     * - The "status" field reflects the expected "DELIVERED" value from the mock response.
     * <p>
     * Dependencies:
     * - MockMvc framework to simulate the web request and response flow.
     * - Mockito to mock the behavior of the deliveryService.
     * - Spring's `MediaType` to define the content type of the request.
     * - JSONPath to validate specific data fields in the response JSON.
     * <p>
     * Exceptions:
     * - An exception is thrown if any unexpected error occurs during test execution.
     */
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