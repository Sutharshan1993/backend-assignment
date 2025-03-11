package com.bayzdelivery.dto;

import java.util.List;

/**
 * Represents the response containing information about the top-performing delivery personnel.
 * <p>
 * This record provides details about the top delivery persons based on their performance metrics,
 * as well as the average commission earned by the top three delivery persons.
 * <p>
 * Fields:
 * - topDeliveryMen: A list of DeliveryManCommission objects representing the top-performing delivery personnel.
 * - averageCommissionOfTop3: The average commission earned by the top three delivery persons.
 */
public record TopDeliveryMenResponse(List<DeliveryManCommission> topDeliveryMen,
                                     double averageCommissionOfTop3
) {
}
