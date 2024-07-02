package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.JewelryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JewelryImageRepository extends JpaRepository<JewelryImage, Integer> {
    @Query(value = "SELECT * FROM Jewelry_Image where jewelry_id = :id" , nativeQuery = true)
    List<JewelryImage> getByJewelryId(int id);
}
