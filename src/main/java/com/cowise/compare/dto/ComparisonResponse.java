package com.cowise.compare.dto;

import java.math.BigDecimal;
import java.util.List;

public record ComparisonResponse(
        BigDecimal amount,
        String amountType,
        String providerCountry,
        List<String> providerTypes,
        List<Provider> providers,
        String sourceCountry,
        String sourceCurrency,
        String targetCountry,
        String targetCurrency) {
}
