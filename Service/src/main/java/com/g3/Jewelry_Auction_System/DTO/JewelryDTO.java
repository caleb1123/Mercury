package com.g3.Jewelry_Auction_System.DTO;

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
    private String jewelryName;
    private String designer;
    private String gemstone;
    private String image;
    private String description;
    private double startingPrice;
    private Boolean status;
    private Collection<Request> requests;
    private JewelryCategory jewelryCategory;
    private Collection<Auction> auctions;
}
