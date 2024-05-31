package com.g3.Jewelry_Auction_System.payload.DTO;

import com.g3.Jewelry_Auction_System.entity.Jewelry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JewelryCategoryDTO {
    private int jewelryCategoryId;
    private String typeName;
    private Collection<Jewelry> jewelries;
}