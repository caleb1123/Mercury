package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Jewelry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JewelryRepository extends JpaRepository<Jewelry, Integer> {

    Optional<Jewelry> findByJewelryId(int jewelryId);
    @Modifying
    @Query("update Jewelry set status = false where jewelryId = :jewelryId")
    void delistJewelry(int jewelryId);
}
