package com.bayzdelivery.service;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
}
