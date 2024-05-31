package converter;

import com.g3.Jewelry_Auction_System.payload.DTO.PostCategoryDTO;
import com.g3.Jewelry_Auction_System.entity.PostCategory;
import org.springframework.stereotype.Component;

@Component
public class PostCategoryConverter {
    public PostCategory toEntity(PostCategoryDTO dto){
        if (dto == null) return null;

        PostCategory entity = new PostCategory();
        entity.setCategoryId(dto.getCategoryId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setPosts(dto.getPosts());
        return entity;
    }

    public PostCategoryDTO toDTO(PostCategory entity){
        if (entity == null) return null;

        PostCategoryDTO dto = new PostCategoryDTO();
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setPosts(entity.getPosts());
        return dto;
    }
}