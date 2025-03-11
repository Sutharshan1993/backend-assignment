package com.bayzdelivery.service;

import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.dto.TopDeliveryMenResponse;
import com.bayzdelivery.exceptions.DeliveryNotFoundException;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Orders;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.repositories.OrdersRepository;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.utils.DeliveryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for {@code DeliveryServiceImpl}.
 * <p>
 * This class tests the functionality of the DeliveryServiceImpl class
 * which is responsible for managing deliveries in the system.
 * <p>
 * The following scenarios are covered in this test class:
 * <p>
 * 1. Creating a delivery with all valid inputs.
 * 2. Handling the case where the associated order does not exist.
 * 3. Handling the case where the delivery person does not exist.
 * 4. Handling the case where the delivery person already has an active delivery.
 * 5. Successfully completing a delivery.
 * 6. Handling the case where a delivery to complete is not found.
 * 7. Handling the case where a delivery to complete is already in a completed state.
 * 8. Retrieving a delivery by its ID.
 * 9. Handling the case where the delivery to retrieve by ID is not found.
 * 10. Retrieving the top-performing delivery personnel by commission within a time range.
 * 11. Handling invalid time ranges when retrieving top delivery personnel.
 * <p>
 * Dependencies are mocked using Mockito, and assertions are used to verify
 * expected behavior under different test scenarios.
 */
@ExtendWith(MockitoExtension.class)
public class DeliveryServiceImplTest {
    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;
    private Delivery delivery;
    private Orders order;
    private Person deliveryMan;

    @BeforeEach
    public void setUp() {
        order = new Orders();
        order.setId(1L);
        order.setOrderPrice(100.0);

        deliveryMan = new Person();
        deliveryMan.setId(1L);
        deliveryMan.setName("John Doe");

        delivery = new Delivery();
        delivery.setId(1L);
        delivery.setOrders(order);
        delivery.setDeliveryMan(deliveryMan);
        delivery.setStartTime(Instant.now());
        delivery.setStatus(DeliveryStatus.ACTIVE);
        delivery.setDistance(10.0);
    }

    @Test
    public void testCreateDelivery_Success() {
        when(ordersRepository.existsById(eq(order.getId()))).thenReturn(true);
        when(personRepository.existsById(eq(deliveryMan.getId()))).thenReturn(true);
        when(deliveryRepository.countByDeliveryManIdAndStatus(eq(deliveryMan.getId()), any(DeliveryStatus.class)))
                .thenReturn(0);
        when(deliveryRepository.save(delivery)).thenReturn(delivery);
        DeliveryResponse response = deliveryService.createDelivery(delivery);

        assertNotNull(response);
        assertEquals(delivery.getId(), response.id());
        assertEquals(delivery.getDeliveryMan().getId(), response.deliveryManId());
        assertEquals(delivery.getStatus().toString(), response.status());

        verify(ordersRepository, times(1)).existsById(eq(order.getId()));
        verify(personRepository, times(1)).existsById(eq(deliveryMan.getId()));
        verify(deliveryRepository, times(1)).countByDeliveryManIdAndStatus(eq(deliveryMan.getId()), any(DeliveryStatus.class));
        verify(deliveryRepository, times(1)).save(delivery);
    }

    @Test
    public void testCreateDelivery_OrderDoesNotExist() {
        when(ordersRepository.existsById(eq(order.getId()))).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deliveryService.createDelivery(delivery);
        });
        assertEquals("Order does not exists", exception.getMessage());

        verify(ordersRepository, times(1)).existsById(eq(order.getId()));
        verify(personRepository, never()).existsById(anyLong());
        verify(deliveryRepository, never()).countByDeliveryManIdAndStatus(anyLong(), any());
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    public void testCreateDelivery_DeliveryManDoesNotExist() {
        when(ordersRepository.existsById(eq(order.getId()))).thenReturn(true);
        when(personRepository.existsById(eq(deliveryMan.getId()))).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deliveryService.createDelivery(delivery);
        });
        assertEquals("Delivery man does not exists", exception.getMessage());

        verify(ordersRepository, times(1)).existsById(eq(order.getId()));
        verify(personRepository, times(1)).existsById(eq(deliveryMan.getId()));
        verify(deliveryRepository, never()).countByDeliveryManIdAndStatus(anyLong(), any());
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    public void testCreateDelivery_DeliveryManHasActiveDelivery() {
        when(ordersRepository.existsById(eq(order.getId()))).thenReturn(true);
        when(personRepository.existsById(eq(deliveryMan.getId()))).thenReturn(true);
        when(deliveryRepository.countByDeliveryManIdAndStatus(eq(deliveryMan.getId()), any(DeliveryStatus.class)))
                .thenReturn(1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deliveryService.createDelivery(delivery);
        });
        assertEquals("Delivery man is already delivering an order", exception.getMessage());

        verify(ordersRepository, times(1)).existsById(eq(order.getId()));
        verify(personRepository, times(1)).existsById(eq(deliveryMan.getId()));
        verify(deliveryRepository, times(1)).countByDeliveryManIdAndStatus(eq(deliveryMan.getId()), any(DeliveryStatus.class));
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    public void testCompleteDelivery_Success() {
        when(deliveryRepository.findById(eq(delivery.getId()))).thenReturn(Optional.of(delivery));
        when(deliveryRepository.save(delivery)).thenReturn(delivery);
        DeliveryResponse response = deliveryService.completeDelivery(15.0, delivery.getId());
        assertNotNull(response);
        assertEquals(delivery.getId(), response.id());
        assertEquals(DeliveryStatus.COMPLETED.toString(), response.status());
        assertNotNull(response.endTime());

        verify(deliveryRepository, times(1)).findById(eq(delivery.getId()));
        verify(deliveryRepository, times(1)).save(delivery);
    }

    @Test
    public void testCompleteDelivery_DeliveryNotFound() {
        when(deliveryRepository.findById(eq(delivery.getId()))).thenReturn(Optional.empty());
        DeliveryNotFoundException exception = assertThrows(DeliveryNotFoundException.class, () -> {
            deliveryService.completeDelivery(15.0, delivery.getId());
        });
        assertEquals("Delivery not found", exception.getMessage());

        verify(deliveryRepository, times(1)).findById(eq(delivery.getId()));
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    public void testCompleteDelivery_DeliveryAlreadyCompleted() {
        delivery.setStatus(DeliveryStatus.COMPLETED);
        when(deliveryRepository.findById(eq(delivery.getId()))).thenReturn(Optional.of(delivery));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            deliveryService.completeDelivery(15.0, delivery.getId());
        });
        assertEquals("Delivery is already completed", exception.getMessage());

        verify(deliveryRepository, times(1)).findById(eq(delivery.getId()));
        verify(deliveryRepository, never()).save(any());
    }

    @Test
    public void testFindById_Success() {
        when(deliveryRepository.findById(eq(delivery.getId()))).thenReturn(Optional.of(delivery));
        DeliveryResponse response = deliveryService.findById(delivery.getId());

        assertNotNull(response);
        assertEquals(delivery.getId(), response.id());
        assertEquals(delivery.getDeliveryMan().getId(), response.deliveryManId());

        verify(deliveryRepository, times(1)).findById(eq(delivery.getId()));
    }

    @Test
    public void testFindById_DeliveryNotFound() {
        when(deliveryRepository.findById(eq(delivery.getId()))).thenReturn(Optional.empty());
        DeliveryNotFoundException exception = assertThrows(DeliveryNotFoundException.class, () -> {
            deliveryService.findById(delivery.getId());
        });
        assertEquals("Delivery not found with ID: " + delivery.getId(), exception.getMessage());

        verify(deliveryRepository, times(1)).findById(eq(delivery.getId()));
    }

    @Test
    public void testGetTopDeliveryMen_Success() {
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();

        List<Object[]> top3DeliveryMen = List.of(
                new Object[]{1L, "John Doe", 100.0, 5L},
                new Object[]{2L, "Jane Doe", 200.0, 10L}
        );

        when(deliveryRepository.findTopDeliveryMenByCommission(startTime, endTime)).thenReturn(top3DeliveryMen);
        TopDeliveryMenResponse response = deliveryService.getTopDeliveryMen(startTime, endTime);
        assertNotNull(response);
        assertEquals(2, response.topDeliveryMen().size());
        assertEquals(150.0, response.averageCommissionOfTop3());

        verify(deliveryRepository, times(1)).findTopDeliveryMenByCommission(startTime, endTime);
    }

    @Test
    public void testGetTopDeliveryMen_InvalidTimeRange() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().minusDays(1);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deliveryService.getTopDeliveryMen(startTime, endTime);
        });
        assertEquals("StartTime must be before endTime", exception.getMessage());

        verify(deliveryRepository, never()).findTopDeliveryMenByCommission(any(), any());
    }
}
