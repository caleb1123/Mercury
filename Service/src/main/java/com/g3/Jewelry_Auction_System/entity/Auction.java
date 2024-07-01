package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "Start date must not be blank")
    private LocalDateTime startDate;

    @Column
    @NotBlank(message = "End date must not be blank")
    private LocalDateTime endDate;

    @Column
    private double currentPrice;

    @Column
    private String status;

    @Column
    private Integer winnerId;

    @ManyToOne
    @JoinColumn(name = "jewelryId")
    private Jewelry jewelry;

    @OneToMany
    @JoinColumn(name = "auction")
    private Collection<Bid> bids;

    @OneToMany
    @JoinColumn(name = "auction")
    private Collection<Payment> payments;

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId=" + auctionId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", currentPrice=" + currentPrice +
                ", status='" + status + '\'' +
                ", winnerId=" + winnerId +
                // do not call toString() on the other side of bidirectional relationships
                '}';
    }
}
