package com.cowise.compare.dto;

import java.math.BigDecimal;

public record Quote(
        String dateCollected,
        DeliveryEstimation deliveryEstimation,
        BigDecimal fee,
        boolean isConsideredMidMarketRate,
        BigDecimal markup,
        BigDecimal rate,
        BigDecimal receivedAmount,
        BigDecimal sendAmount,
        String sourceCountry,
        String targetCountry) {
}
