package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ERequestStatus status;

    @Column
    private LocalDate evaluationDate;

    @Column
    private LocalDate deliveryDate;

    @Column
    @PositiveOrZero(message = "Preliminary price must be zero or positive")
    private double preliminaryPrice;

    @Column
    @PositiveOrZero(message = "Final price must be zero or positive")
    private double finalPrice;


    @ManyToOne
    @JoinColumn(name = "jewelryId")
    @NotNull(message = "Jewelry is required")
    private Jewelry jewelry;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    @NotNull(message = "Account is required")
    private Account account;
}
