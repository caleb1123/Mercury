package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Bid;
import com.g3.Jewelry_Auction_System.payload.response.BidResponse;
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
    @Query(value= "SELECT b.bid_amount,\n" +
            "       a.user_name,\n" +
            "       b.bid_time\n" +
            "FROM bid AS b\n" +
            "JOIN account AS a\n" +
            "ON b.account_id = a.account_id WHERE auction_id = :auctionId", nativeQuery = true)
    List<Object[]> getBidResponseListByAuctionId(int auctionId);

    @Query(value = "SELECT TOP 1 [bid_amount] " +
            "FROM [JewelryAuctionSystem].[dbo].[bid] " +
            "WHERE [auction_id] = :auctionId " +
            "ORDER BY [bid_amount] DESC", nativeQuery = true)
    Integer findHighestBidAmountByAuctionId(int auctionId);

    @Query(value = "SELECT TOP 1 b.account_id " +
            "FROM bid b " +
            "WHERE b.auction_id = :auctionId " +
            "ORDER BY b.bid_amount DESC", nativeQuery = true)
    Integer findHighestBidderId(int auctionId);
}
