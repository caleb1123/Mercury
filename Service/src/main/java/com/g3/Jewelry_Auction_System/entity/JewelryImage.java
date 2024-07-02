package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "JewelryImage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JewelryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jewelryImageId;

    @Column
    private String fileId;
    @Column
    private String jewelryImageURL;

    @ManyToOne
    @JoinColumn(name = "jewelryId")
    private Jewelry jewelry;
}
