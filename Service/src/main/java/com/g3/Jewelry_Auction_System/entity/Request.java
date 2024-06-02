package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    @Column
    private LocalDate requestDate;

    @Column
    private Boolean status;

    @Column
    private LocalDate evaluationDate;

    @Column
    private double preliminaryPrice;

    @Column
    private double finalPrice;

    @ManyToOne
    @JoinColumn(name = "jewelryId")
    private Jewelry jewelry;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Account account;
}
