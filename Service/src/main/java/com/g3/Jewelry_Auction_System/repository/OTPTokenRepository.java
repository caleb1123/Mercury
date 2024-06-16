package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.OTPToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPTokenRepository extends JpaRepository<OTPToken,Integer> {
    OTPToken findByEmailAndOtp(String email, String otp);
}
