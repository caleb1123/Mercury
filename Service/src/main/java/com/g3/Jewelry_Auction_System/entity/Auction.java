package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "Auction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionId;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private double currentPrice;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "jewelryId")
    private Jewelry jewelry;

    @OneToMany
    @JoinColumn(name = "auction")
    private Collection<Bid> bids;

    @OneToMany
    @JoinColumn(name = "auction")
    private Collection<Payment> payments;
}
