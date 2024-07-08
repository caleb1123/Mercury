package com.g3.Jewelry_Auction_System.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JewelryImageDTO {
    private int jewelryImageId;
    private String jewelryImageURL;
    private String fileId;
    private boolean status;
    private int jewelryId;
}
