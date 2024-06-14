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
}
