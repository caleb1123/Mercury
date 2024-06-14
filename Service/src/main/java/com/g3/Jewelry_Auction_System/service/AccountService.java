package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.payload.response.AccountSearchByRoleResponse;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    void deactivateAccount(String userName);
    void updateAccount(AccountDTO accountDTO, String username);
    List<AccountDTO> getAccountList();
    AccountResponse getMyInfor();

    List<AccountResponse> searchAccountByName(String name);

    List<AccountSearchByRoleResponse> searchAccountByRoleName(String roleName);
}
