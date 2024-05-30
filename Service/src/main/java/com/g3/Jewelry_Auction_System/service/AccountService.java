package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.entity.Account;

public interface AccountService {
    Account createAccount(AccountDTO accountDTO);
    void deactivateAccount(String userName);
    Account updateAccount(AccountDTO accountDTO);
}
