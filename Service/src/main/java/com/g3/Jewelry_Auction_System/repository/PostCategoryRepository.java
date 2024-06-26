package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Integer> {
    List<PostCategory> findAll();
}
