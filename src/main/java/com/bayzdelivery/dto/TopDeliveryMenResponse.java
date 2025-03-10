package com.bayzdelivery.dto;

import java.util.List;

public record TopDeliveryMenResponse(List<DeliveryManCommission> topDeliveryMen,
                                     double averageCommissionOfTop3
) {
}
