package com.g3.Jewelry_Auction_System.payload.DTO;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.JewelryCategory;
import com.g3.Jewelry_Auction_System.entity.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JewelryDTO {
    private int jewelryId;
    private String jewelryCode;
    private String jewelryName;
    private String designer;
    private String gemstone;
    private String description;
    private String condition;
    private double estimate;
    private double startingPrice;
    private Boolean status;
    private int jewelryCategoryId;
}
