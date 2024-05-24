package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "AuctionSession")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionSessionId;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "auctionId")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "AssignEmploymentId")
    private Account account;

}
