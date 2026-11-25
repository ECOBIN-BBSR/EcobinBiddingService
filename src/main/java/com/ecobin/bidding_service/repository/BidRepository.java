package com.ecobin.bidding_service.repository;

import com.ecobin.bidding_service.entity.Bid;
import com.ecobin.bidding_service.enums.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid, UUID> {

    List<Bid> findByProductId(UUID productId);

    List<Bid> findByCollectorId(int collectorId);

    List<Bid> findByProductIdAndStatus(UUID productId, BidStatus status);
}