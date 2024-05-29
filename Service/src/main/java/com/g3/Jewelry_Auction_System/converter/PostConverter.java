package converter;

import com.g3.Jewelry_Auction_System.payload.DTO.PostDTO;
import com.g3.Jewelry_Auction_System.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post toEntity(PostDTO dto){
        if (dto==null) return null;

        Post post = new Post();
        post.setPostId(dto.getPostId());
        post.setTitle(dto.getTitle());
        post.setPostDate(dto.getPostDate());
        post.setContent(dto.getContent());
        post.setStatus(dto.getStatus());
        post.setAccount(dto.getAccount());
        post.setPostCategory(dto.getPostCategory());
        return post;
    }
    public PostDTO toDTO(Post post){
        if (post==null) return null;

        PostDTO dto = new PostDTO();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setPostDate(post.getPostDate());
        dto.setContent(post.getContent());
        dto.setStatus(post.getStatus());
        dto.setAccount(post.getAccount());
        dto.setPostCategory(post.getPostCategory());
        return dto;
    }
}
