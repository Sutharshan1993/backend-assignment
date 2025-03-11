package com.bayzdelivery.service;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.utils.DeliveryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This test class is responsible for testing the functionality of the DeliveryCheckServiceImpl class.
 * It uses the Mockito framework to mock dependencies and verify the behavior of the service methods under various scenarios.
 * <p>
 * The class contains unit tests for the following:
 * - Handling of overdue deliveries, including scenarios with no overdue deliveries and multiple overdue deliveries.
 * - Notifying customer support for specific delivery instances.
 * - Behavior of the service when the overdue threshold is set to zero or negative values.
 * - Handling repository exceptions during delivery checks.
 * - Scenarios involving no active deliveries, mixed delivery statuses, and deliveries with future start times.
 * <p>
 * Each test case uses mocking to simulate different states and interactions with the DeliveryRepository
 * and verifies that the methods behave as expected.
 * <p>
 * The setup for each test case includes using ReflectionTestUtils to set specific fields on the DeliveryCheckServiceImpl instance.
 */
@ExtendWith(MockitoExtension.class)
public class DeliveryCheckServiceImplTest {
    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryCheckServiceImpl deliveryCheckService;

    @BeforeEach
    public void setUp() {
        // Set the overdueThresholdMinutes value using ReflectionTestUtils
        ReflectionTestUtils.setField(deliveryCheckService, "overdueThresholdMinutes", 45);
    }

    @Test
    public void testCheckDelivery_NoOverdueDeliveries() {
        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class))).thenReturn(Collections.emptyList());
        deliveryCheckService.checkDelivery();
        verify(deliveryRepository, times(1)).findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }

    @Test
    public void testCheckDelivery_WithOverdueDeliveries() {

        Delivery overdueDelivery1 = new Delivery();
        overdueDelivery1.setId(1L);
        overdueDelivery1.setStartTime(Instant.now().minusSeconds(60 * 60)); // 1 hour ago

        Delivery overdueDelivery2 = new Delivery();
        overdueDelivery2.setId(2L);
        overdueDelivery2.setStartTime(Instant.now().minusSeconds(60 * 90)); // 1.5 hours ago

        List<Delivery> overdueDeliveries = List.of(overdueDelivery1, overdueDelivery2);

        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class)))
                .thenReturn(overdueDeliveries);

        deliveryCheckService.checkDelivery();

        verify(deliveryRepository, times(1))
                .findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }

    @Test
    public void testNotifyCustomerSupport() {

        Delivery delivery = new Delivery();
        delivery.setId(1L);
        delivery.setStartTime(Instant.now());

        deliveryCheckService.notifyCustomerSupport(delivery);

    }

    @Test
    public void testCheckDelivery_ZeroOverdueThreshold() {

        ReflectionTestUtils.setField(deliveryCheckService, "overdueThresholdMinutes", 0); // Set threshold to 0
        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class)))
                .thenReturn(Collections.emptyList());

        deliveryCheckService.checkDelivery();

        verify(deliveryRepository, times(1))
                .findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }

    @Test
    public void testCheckDelivery_NegativeOverdueThreshold() {
        ReflectionTestUtils.setField(deliveryCheckService, "overdueThresholdMinutes", -10); // Set threshold to -10
        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class)))
                .thenReturn(Collections.emptyList());

        deliveryCheckService.checkDelivery();

        verify(deliveryRepository, times(1))
                .findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }

    @Test
    public void testCheckDelivery_RepositoryThrowsException() {
        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class))).thenThrow(new RuntimeException("Database connection failed"));
        deliveryCheckService.checkDelivery();
        verify(deliveryRepository, times(1)).findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }

    @Test
    public void testCheckDelivery_MultipleOverdueDeliveries() {

        Delivery overdueDelivery1 = new Delivery();
        overdueDelivery1.setId(1L);
        overdueDelivery1.setStartTime(Instant.now().minusSeconds(60 * 60)); // 1 hour ago

        Delivery overdueDelivery2 = new Delivery();
        overdueDelivery2.setId(2L);
        overdueDelivery2.setStartTime(Instant.now().minusSeconds(60 * 90)); // 1.5 hours ago

        List<Delivery> overdueDeliveries = List.of(overdueDelivery1, overdueDelivery2);

        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class)))
                .thenReturn(overdueDeliveries);

        deliveryCheckService.checkDelivery();

        verify(deliveryRepository, times(1))
                .findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }

    @Test
    public void testNotifyCustomerSupport_NullDelivery() {
        deliveryCheckService.notifyCustomerSupport(null);
    }

    @Test
    public void testCheckDelivery_NoActiveDeliveries() {
        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class)))
                .thenReturn(Collections.emptyList());
        deliveryCheckService.checkDelivery();
        verify(deliveryRepository, times(1))
                .findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }

    @Test
    public void testCheckDelivery_MixedStatusDeliveries() {
        Delivery activeDelivery = new Delivery();
        activeDelivery.setId(1L);
        activeDelivery.setStatus(DeliveryStatus.ACTIVE);
        activeDelivery.setStartTime(Instant.now().minusSeconds(60 * 60)); // 1 hour ago

        Delivery completedDelivery = new Delivery();
        completedDelivery.setId(2L);
        completedDelivery.setStatus(DeliveryStatus.COMPLETED);
        completedDelivery.setStartTime(Instant.now().minusSeconds(60 * 90)); // 1.5 hours ago

        List<Delivery> deliveries = List.of(activeDelivery, completedDelivery);

        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class)))
                .thenReturn(List.of(activeDelivery));
        deliveryCheckService.checkDelivery();
        verify(deliveryRepository, times(1))
                .findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }

    @Test
    public void testCheckDelivery_FutureStartTime() {
        Delivery futureDelivery = new Delivery();
        futureDelivery.setId(1L);
        futureDelivery.setStartTime(Instant.now().plusSeconds(60 * 60)); // 1 hour in the future

        when(deliveryRepository.findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class)))
                .thenReturn(Collections.emptyList());
        deliveryCheckService.checkDelivery();
        verify(deliveryRepository, times(1))
                .findByStatusAndStartTimeBefore(eq(DeliveryStatus.ACTIVE), any(Instant.class));
        verifyNoMoreInteractions(deliveryRepository);
    }
}
