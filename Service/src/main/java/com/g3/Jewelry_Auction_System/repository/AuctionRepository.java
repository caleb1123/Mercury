package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Auction;
import com.g3.Jewelry_Auction_System.entity.Jewelry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    List<Auction> findByStartDateBeforeAndEndDateAfterAndStatus(LocalDateTime startDate, LocalDateTime endDate, String status);
    List<Auction> findByEndDateBeforeAndStatus(LocalDateTime endDate, String status);

    @Query(value="SELECT a.*," +
            "  DATEDIFF(hour, CURRENT_TIMESTAMP, a.start_date) AS time_difference_hours " +
            "FROM Auction a " +
            "WHERE a.start_date > CURRENT_TIMESTAMP AND a.status = 'Pending' " +
            "ORDER BY time_difference_hours ASC", nativeQuery = true)
    List<Object[]> getUpcomingAuctions();

    @Query(value="SELECT " +
            "    auction_id, " +
            "    current_price, " +
            "    end_date, " +
            "    start_date, " +
            "    status, " +
            "    winner_id, " +
            "    jewelry_id, " +
            "    CASE " +
            "        WHEN DATEDIFF(day, GETDATE(), end_date) < 0 THEN 0 " +
            "        ELSE DATEDIFF(day, GETDATE(), end_date) " +
            "    END AS days_to_end " +
            "FROM " +
            "    JewelryAuctionSystem.dbo.auction " +
            "WHERE " +
            "    status = 'Ongoing' " +
            "ORDER BY " +
            "    days_to_end ASC", nativeQuery = true)
    List<Object[]> findOngoingAuctionsOrderByDaysToEnd();

    List<Auction> findByEndDateBeforeAndWinnerIdIsNull(LocalDateTime endDate);
    List<Auction> getAuctionByStatus(String status);
    Auction findAuctionByAuctionId(int auctionId);

    @Query(value = "SELECT * from Auction where winner_id = :id and status = 'Ended'", nativeQuery = true)
    List<Auction> getAuctionsByWinnerId (int id);

    @Query(value = "SELECT * FROM Auction a WHERE a.status != 'Deleted' AND a.jewelry_id = :id AND a.auction_id =" +
            "(SELECT MAX(auction_id) FROM Auction WHERE jewelry_id = :id AND status != 'Deleted')", nativeQuery = true)
    Optional<Auction> getLastAuctionByJewelryId(int id);

    @Query(value = "SELECT a.* FROM Auction a WHERE a.auction_id IN ( SELECT MAX(auction_id)FROM Auction GROUP BY jewelry_id) AND a.status = :status", nativeQuery = true)
    List<Auction> getLatestAuctionByStatus(String status);
}