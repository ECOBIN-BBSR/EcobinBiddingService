package com.ecobin.bidding_service.service;

import com.ecobin.bidding_service.dto.BidRequest;
import com.ecobin.bidding_service.dto.BidResponse;
import com.ecobin.bidding_service.entity.Bid;
import com.ecobin.bidding_service.enums.BidStatus;
import com.ecobin.bidding_service.repository.BidRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    // 1. Place a new bid
    @Override
    public BidResponse placeBid(BidRequest request) {

        Bid bid = Bid.builder()
                .productId(request.productId())
                .collectorId(request.collectorId())
                .bidPrice(request.bidPrice())
                .status(BidStatus.PENDING)
                .build();

        Bid savedBid = bidRepository.save(bid);

        return mapToResponse(savedBid);
    }

    // 2. Update an existing pending bid (TO BE IMPLEMENTED IN FUTURE)
    @Override
    public BidResponse updateBid(
            UUID bidId,
            int collectorId,
            BigDecimal newPrice
    ) {

        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Bid not found"));

        if (bid.getCollectorId() != (collectorId)) {
            throw new IllegalArgumentException(
                    "You are not authorized to update this bid");
        }

        if (bid.getStatus() != BidStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending bids can be updated");
        }

        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Bid price must be greater than zero");
        }

        bid.setBidPrice(newPrice);
        bid.setUpdatedPriceTime(LocalDateTime.now());

        Bid updatedBid = bidRepository.save(bid);

        return mapToResponse(updatedBid);
    }

    // 3. Get bid by ID
    @Override
    @Transactional(readOnly = true)
    public BidResponse getBidById(UUID bidId) {

        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Bid not found"));

        return mapToResponse(bid);
    }

    // 4. Get all bids for a product
    @Override
    @Transactional(readOnly = true)
    public List<BidResponse> getBidsByProduct(UUID productId) {

        return bidRepository.findByProductId(productId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 5. Get all bids by a collector
    @Override
    @Transactional(readOnly = true)
    public List<BidResponse> getBidsByCollector(int collectorId) {

        return bidRepository.findByCollectorId(collectorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Convert Entity to Response DTO
    private BidResponse mapToResponse(Bid bid) {

        return new BidResponse(
                bid.getId(),
                bid.getProductId(),
                bid.getCollectorId(),
                bid.getBidPrice(),
                bid.getStatus(),
                bid.getBidDateTime(),
                bid.getUpdatedPriceTime(),
                bid.getCreatedAt(),
                bid.getUpdatedAt()
        );
    }
}