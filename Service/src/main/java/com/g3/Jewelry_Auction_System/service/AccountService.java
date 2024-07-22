package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.request.CreateAccountRequest;
import com.g3.Jewelry_Auction_System.payload.request.SignUpRequest;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.payload.response.AccountSearchByRoleResponse;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(CreateAccountRequest accountDTO);
    void deactivateAccount(String userName);
    Account updateAccount(AccountDTO accountDTO, String username);
    List<AccountDTO> getAccountList();
    AccountResponse getMyInfor();
    AccountDTO getAccountByUsername(String username);
    List<AccountResponse> searchAccountByName(String name);

    List<AccountSearchByRoleResponse> searchAccountByRoleName(String roleName);
    AccountDTO getAccountByAccountId(int id);
}
