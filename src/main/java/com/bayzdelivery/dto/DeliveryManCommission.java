package com.bayzdelivery.dto;

public record DeliveryManCommission(Long deliveryManId,
                                    String deliveryManName,
                                    double totalCommission,
                                    long totalCompletedOrders,
                                    double averageCommissionPerOrder) {
}
