package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    Optional<Bid> findById(int id);
    @Query(value = "SELECT * FROM bid WHERE auction_id = :auctionId AND bid_amount = (SELECT MAX(bid_amount) FROM bid WHERE auction_id = :auctionId);", nativeQuery = true)
    Optional<Bid> getHighestBidAmount(int auctionId);
    @Query(value = "SELECT * FROM bid WHERE auction_id = :auctionId", nativeQuery = true)
    List<Bid> findByAuctionId(int auctionId);
}
