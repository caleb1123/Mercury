package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountConverter accountConverter;
    @Override
    public Account createAccount(AccountDTO accountDTO) {
        Account createAccount = accountConverter.toEntity(accountDTO);
        accountRepository.save(createAccount);
        return createAccount;
    }
}
