package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    Optional<Auction> findById(int id);
    List<Auction> findByJewelry(Jewelry jewelry);
}
