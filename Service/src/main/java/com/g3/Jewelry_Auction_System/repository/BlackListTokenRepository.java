package com.g3.Jewelry_Auction_System.repository;

import com.g3.Jewelry_Auction_System.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, String> {
}
