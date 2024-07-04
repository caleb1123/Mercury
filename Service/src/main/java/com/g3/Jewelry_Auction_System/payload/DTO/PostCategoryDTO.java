package com.g3.Jewelry_Auction_System.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostCategoryDTO {
    private int categoryId;
    private String categoryName;
}
