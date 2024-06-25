package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    Optional<Auction> findById(int id);
    List<Auction> findByJewelry(Jewelry jewelry);
    @Query(value="SELECT \n" +
            "    Account.account_id,\n" +
            "    Account.user_name,\n" +
            "    Bid.bid_amount,\n" +
            "    Jewelry.jewelry_id,\n" +
            "    Jewelry.jewelry_name,\n" +
            "    Auction.auction_id\n" +
            "FROM \n" +
            "    Account\n" +
            "JOIN \n" +
            "    Bid ON Account.account_id = Bid.account_id\n" +
            "JOIN \n" +
            "    Auction ON Auction.auction_id = Bid.auction_id\n" +
            "JOIN \n" +
            "    Jewelry ON Jewelry.jewelry_id = Auction.jewelry_id\n" +
            "WHERE \n" +
            "    Bid.bid_amount = (\n" +
            "        SELECT MAX(bid_amount) FROM Bid WHERE auction_id = Auction.auction_id\n" +
            "    )\n" +
            "\tAND Auction.auction_id = :auctionId", nativeQuery = true)
    List<Object[]> getWinnerByAuctionId(int auctionId);
}
