package com.g3.Jewelry_Auction_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Category name is required")
    private EJewelCategory categoryName;

    @Column
    @NotBlank(message = "Image URL is required")
    private String image;

    @OneToMany(mappedBy = "jewelryCategory")
    private Collection<Jewelry> jewelries;


}
