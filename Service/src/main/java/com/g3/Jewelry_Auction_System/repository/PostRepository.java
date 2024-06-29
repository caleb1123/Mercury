package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SElECT * FROM post WHERE title LIKE '%'+:title+'%'", nativeQuery = true)
    List<Post> findByTitle(@Param("title") String title);

    @Query(value = "select * from post full outer join post_category " +
            "on post.post_category_id = post_category.category_id " +
            "where category_name like :categoryName", nativeQuery = true)
    List<Post> getPostsByCategoryName (@Param(value = "categoryName") String categoryName);

    @Query(value = "select * from post where post_category_id = :categoryId", nativeQuery = true)
    List<Post> getPostsByCategoryId (@Param(value = "categoryId") int categoryId);

    @Query(value = "select * from post where status = 1", nativeQuery = true)
    List<Post> findAll();
}
