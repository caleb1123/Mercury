package com.g3.Jewelry_Auction_System.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDTO {
    private int postId;
    private String title;
    private LocalDate postDate;
    private String content;
    private Boolean status;
    private int accountId;
    private int categoryId;
}
