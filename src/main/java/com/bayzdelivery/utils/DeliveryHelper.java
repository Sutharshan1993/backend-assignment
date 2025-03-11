package com.bayzdelivery.utils;

import com.bayzdelivery.dto.DeliveryManCommission;
import com.bayzdelivery.dto.DeliveryResponse;
import com.bayzdelivery.model.Delivery;

/**
 * Utility class for handling delivery-related data transformations.
 * <p>
 * This class consists of static methods to map delivery-related entities
 * to their corresponding response or DTO objects. It also provides
 * internal helper methods for data extraction and computation required
 * within these mappings.
 * <p>
 * The class is designed to be non-instantiable and should only be used
 * for its static utility methods.
 */
public class DeliveryHelper {

    private DeliveryHelper() {
    }

    /**
     * Maps an array of objects to a DeliveryManCommission record.
     * <p>
     * The method interprets the input data as a row containing details about
     * a delivery man's commission, total orders completed, and performs
     * necessary data conversions as well as computations to determine
     * the average commission per order.
     *
     * @param row an Object array containing delivery man commission data. Expected to have:
     *            - row[0]: Delivery man ID (Long),
     *            - row[1]: Delivery man name (String),
     *            - row[2]: Total commission (Double),
     *            - row[3]: Total completed orders (Long).
     * @return a DeliveryManCommission record encapsulating the parsed and computed values.
     * @throws IllegalArgumentException if the input array is null or has less than 4 elements.
     */
    public static DeliveryManCommission mapToDeliveryManCommission(Object[] row) {
        if ( row == null || row.length < 4 ) {
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
