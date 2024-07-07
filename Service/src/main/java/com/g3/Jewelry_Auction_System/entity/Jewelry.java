package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "Jewelry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jewelry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jewelryId;

    @Column(unique = true)
    private String jewelryCode;

    @Column
    @NotBlank(message = "Jewelry name is required")
    @Size(min = 6, message = "Jewelry name must be at least 6 character")
    private String jewelryName;

    @Column
    @NotBlank(message = "Designer is required")
    @Size(min = 6, message = "Designer must be at least 6 character")
    private String designer;

    @Column
    private String gemstone;


    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column
    @NotBlank(message = "Condition is required")
    private String condition;

    @Column
    @Positive(message = "Estimate must be a positive number")
    private double estimate;

    @Column
    @Positive(message = "Starting price must be a positive number")
    private double startingPrice;

    @Column
    private Boolean status;

    @OneToMany(mappedBy = "jewelry")
    private Collection<Request> requests;

    @ManyToOne
    @JoinColumn(name = "jewelryCategoryId")
    private JewelryCategory jewelryCategory;

    @OneToMany(mappedBy = "jewelry")
    private Collection<Auction> auctions;

    @OneToMany(mappedBy = "jewelry")
    private Collection<JewelryImage> jewelryImages;
}
