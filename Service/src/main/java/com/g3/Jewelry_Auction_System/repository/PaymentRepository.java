package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByPaymentStatus(String status);
    Payment findByPaymentCode(int paymentCode);
    @Query(value = "SELECT * FROM Payment WHERE payment_code = :paymentCode", nativeQuery = true)
    Payment getPaymentByCode(String paymentCode);
    @Query(value = "SELECT * from Payment where auction_id = :auctionId and account_id = :accountId", nativeQuery = true)
    List<Payment> findByAuctionAndAccountIds(int auctionId, int accountId);
}
