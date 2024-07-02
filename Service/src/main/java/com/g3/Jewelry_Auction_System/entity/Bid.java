package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bid")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bidId;

    @Column
    private double bidAmount;

    @Column
    private LocalDateTime bidTime;

    @ManyToOne
    @JoinColumn(name = "auctionId")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", bidAmount=" + bidAmount +
                // other fields, but don't include the ones that cause circular references
                '}';
    }
}
