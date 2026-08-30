package com.cowise.compare.dto;

public record DeliveryEstimation(
        DurationRange duration,
        boolean providerGivesEstimate) {
}
