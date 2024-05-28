package com.g3.Jewelry_Auction_System.DTO;

import com.g3.Jewelry_Auction_System.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostCategoryDTO {
    private int categoryId;
    private String categoryName;
    private Collection<Post> posts;
}
