package com.bayzdelivery.dto;

/**
 * A record that represents the commission details of a delivery person.
 * <p>
 * This record is utilized to encapsulate data regarding a delivery person's performance,
 * including their total commission, the number of orders completed, and the average commission per order.
 * <p>
 * Fields:
 * - deliveryManId: Unique identifier of the delivery person.
 * - deliveryManName: Name of the delivery person.
 * - totalCommission: Total commission earned by the delivery person.
 * - totalCompletedOrders: Total number of orders successfully completed by the delivery person.
 * - averageCommissionPerOrder: The average commission earned per order by the delivery person.
 */
public record DeliveryManCommission(Long deliveryManId,
                                    String deliveryManName,
                                    double totalCommission,
                                    long totalCompletedOrders,
                                    double averageCommissionPerOrder) {
}
