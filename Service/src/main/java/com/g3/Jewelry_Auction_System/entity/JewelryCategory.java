package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "JewelryCategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JewelryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jewelryCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private EJewelCategory categoryName;

    @Column
    private String image;

    @OneToMany(mappedBy = "jewelryCategory")
    private Collection<Jewelry> jewelries;


}
