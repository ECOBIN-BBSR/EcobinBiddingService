package com.ecobin.bidding_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record BidRequest(

        @NotNull(message = "Product ID is required")
        UUID productId,

        @NotNull(message = "Collector ID is required")
        int collectorId,

        @NotNull(message = "Bid price is required")
        @DecimalMin(value = "0.01", message = "Bid price must be greater than zero")
        BigDecimal bidPrice

) {}