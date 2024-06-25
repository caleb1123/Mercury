package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.JewelryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JewelryCategoryRepository extends JpaRepository<JewelryCategory, Integer> {

    @Query(value = "Select * from jewelry_category where category_name like '%'+:category+'%'", nativeQuery = true)
    List<JewelryCategory> getJewelriesByCategory(@Param("category") String category);
}
