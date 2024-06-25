package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Integer> {
    @Query(value = "SElECT * FROM post_category WHERE category_name LIKE '%'+:cate+'%'", nativeQuery = true)
    List<PostCategory> findAllByCateName(@Param("cate") String cate);
}
