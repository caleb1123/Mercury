package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @Column
    private LocalDate paymentDate;

    @Column
    private double amount;

    @Column
    private String paymentMethod;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "auctionId")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
}
