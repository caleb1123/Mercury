package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.entity.Account;

public interface AccountService {
    Account createAccount(AccountDTO accountDTO);
}
