package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    void deactivateAccount(String userName);
    void updateAccount(AccountDTO accountDTO);
    List<AccountDTO> getAccountList();
}
