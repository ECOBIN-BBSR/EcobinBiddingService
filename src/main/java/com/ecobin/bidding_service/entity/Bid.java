package com.ecobin.bidding_service.entity;

import com.ecobin.bidding_service.enums.BidStatus;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "bids",
        indexes = {
                @Index(name = "idx_bid_product", columnList = "product_id"),
                @Index(name = "idx_bid_collector", columnList = "collector_id"),
                @Index(name = "idx_bid_status", columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "collector_id", nullable = false)
    private int collectorId;

    @Column(
            name = "bid_price",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal bidPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BidStatus status;

    @Column(name = "bid_date_time", nullable = false)
    private LocalDateTime bidDateTime;

    @Column(name = "updated_price_time")
    private LocalDateTime updatedPriceTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        this.bidDateTime = now;
        this.createdAt = now;
        this.updatedAt = now;

        if (this.status == null) {
            this.status = BidStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {

        this.updatedAt = LocalDateTime.now();
    }
}
