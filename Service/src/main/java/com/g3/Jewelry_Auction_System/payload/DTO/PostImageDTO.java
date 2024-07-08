package com.g3.Jewelry_Auction_System.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostImageDTO {
    private int postImageId;
    private String postImageURL;
    private String fileId;
    private boolean status;
    private int postId;
}
