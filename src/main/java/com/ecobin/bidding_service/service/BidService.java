package com.ecobin.bidding_service.service;

import com.ecobin.bidding_service.dto.BidRequest;
import com.ecobin.bidding_service.dto.BidResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BidService {

    BidResponse placeBid(BidRequest request);

    BidResponse updateBid(UUID bidId, int collectorId, BigDecimal newPrice);

    BidResponse getBidById(UUID bidId);

    List<BidResponse> getBidsByProduct(UUID productId);

    List<BidResponse> getBidsByCollector(int collectorId);
}