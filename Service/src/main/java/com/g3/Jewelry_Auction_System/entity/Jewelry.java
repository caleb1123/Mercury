package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "Jewelry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jewelry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jewelryId;

    @Column
    private String jewelryName;

    @Column
    private String designer;

    @Column
    private String gemstone;

    @Column
    private String image;

    @Column
    private String description;

    @Column
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
}
