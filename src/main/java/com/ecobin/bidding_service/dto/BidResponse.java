package com.ecobin.bidding_service.dto;

import com.ecobin.bidding_service.enums.BidStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BidResponse(

        UUID id,

        UUID productId,

        int collectorId,

        BigDecimal bidPrice,

        BidStatus status,

        LocalDateTime bidDateTime,

        LocalDateTime updatedPriceTime,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {}