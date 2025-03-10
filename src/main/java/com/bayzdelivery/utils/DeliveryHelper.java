package com.bayzdelivery.utils;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.model.Delivery;

public class DeliveryHelper {

    private DeliveryHelper() {}
    public static DeliveryManCommission mapToDeliveryManCommission(Object[] row) {
        if (row == null || row.length < 4) {
            throw new IllegalArgumentException("Invalid data format for DeliveryManCommission mapping.");
        }

        return new DeliveryManCommission(
                extractLong(row[0]),
                extractString(row[1]),
                extractDouble(row[2]),
                extractLong(row[3]),
                calculateAverageCommission(row[2], row[3])
        );
    }

    // Helper methods to get Data
    private static Long extractLong(Object obj) {
        return (obj instanceof Number number) ? number.longValue() : 0L;
    }

    private static double extractDouble(Object obj) {
        return (obj instanceof Number number) ? number.doubleValue() : 0.0;
    }

    private static String extractString(Object obj) {
        return obj instanceof String str ? str : "";
    }

    private static double calculateAverageCommission(Object totalCommission, Object totalOrders) {
        long orders = extractLong(totalOrders);
        return (orders > 0) ? extractDouble(totalCommission) / orders : 0.0;
    }

    public static DeliveryResponse mapToDeliveryResponse(Delivery delivery) {
        return new DeliveryResponse(
                delivery.getId(),
                delivery.getDeliveryMan().getId(),
                delivery.getStartTime(),
                delivery.getEndTime(),
                delivery.getStatus().toString(),
                delivery.getCommission(),
                delivery.getDistance()
        );
    }
}
