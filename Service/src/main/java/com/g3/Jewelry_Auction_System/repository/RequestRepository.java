package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Jewelry;
import com.g3.Jewelry_Auction_System.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    Optional<Request> findByRequestId(int id);
    @Query(value = "SELECT * FROM Request WHERE jewelry_id = :id", nativeQuery = true)
    List<Request> findByJewelryId(int id);
    @Query(value = "SELECT * FROM Request WHERE seller_id = :id", nativeQuery = true)
    List<Request> getRequestsBySellerId(int id);
    @Query(value = "SELECT * FROM Request where status = :status", nativeQuery = true)
    List<Request> getRequestByStatus(String status);
}
