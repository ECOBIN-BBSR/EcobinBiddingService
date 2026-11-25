package com.ecobin.bidding_service.controller;

import com.ecobin.bidding_service.dto.BidRequest;
import com.ecobin.bidding_service.dto.BidResponse;
import com.ecobin.bidding_service.service.BidService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bids")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    // 1. Place a new bid
    @PostMapping
    public ResponseEntity<BidResponse> placeBid(
            @Valid @RequestBody BidRequest request) {

        BidResponse response = bidService.placeBid(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 2. Update an existing bid
    @PutMapping("/{bidId}")
    public ResponseEntity<BidResponse> updateBid(
            @PathVariable UUID bidId,
            @RequestParam int collectorId,
            @RequestParam BigDecimal newPrice) {

        BidResponse response = bidService.updateBid(
                bidId,
                collectorId,
                newPrice
        );

        return ResponseEntity.ok(response);
    }

    // 3. Get a bid by ID
    @GetMapping("/{bidId}")
    public ResponseEntity<BidResponse> getBidById(
            @PathVariable UUID bidId) {

        return ResponseEntity.ok(
                bidService.getBidById(bidId)
        );
    }

    // 4. Get all bids for a product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<BidResponse>> getBidsByProduct(
            @PathVariable UUID productId) {

        return ResponseEntity.ok(
                bidService.getBidsByProduct(productId)
        );
    }

    // 5. Get all bids by a collector
    @GetMapping("/collector/{collectorId}")
    public ResponseEntity<List<BidResponse>> getBidsByCollector(
            @PathVariable int collectorId) {

        return ResponseEntity.ok(
                bidService.getBidsByCollector(collectorId)
        );
    }
}