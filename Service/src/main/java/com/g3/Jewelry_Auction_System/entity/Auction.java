package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "Auction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private double currentPrice;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "jewelryId")
    private Jewelry jewelry;

    @ManyToOne
    @JoinColumn(name = "winnerId")
    private Account account;

    @OneToMany
    @JoinColumn(name = "auction")
    private Collection<Bid> bids;

    @OneToMany
    @JoinColumn(name = "auction")
    private Collection<Payment> payments;
}
