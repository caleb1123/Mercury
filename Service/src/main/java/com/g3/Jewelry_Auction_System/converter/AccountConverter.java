package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null){
            return null;
        }

        Account entity = new Account();
        entity.setAccountId(accountDTO.getAccountId());
        entity.setFullName(accountDTO.getFullName());
        entity.setUserName(accountDTO.getUserName());
        entity.setPassword(accountDTO.getPassword());
        entity.setAddress(accountDTO.getAddress());
        entity.setDob(accountDTO.getDob());
        entity.setEmail(accountDTO.getEmail());
        entity.setSex(accountDTO.getSex());
        entity.setPhone(accountDTO.getPhone());
        entity.setStatus(accountDTO.getStatus());
        return entity;
    }

    public AccountDTO toDTO(Account account) {
        if (account == null){
            return null;
        }

        AccountDTO dto = new AccountDTO();
        dto.setAccountId(account.getAccountId());
        dto.setFullName(account.getFullName());
        dto.setUserName(account.getUserName());
        dto.setPassword(account.getPassword());
        dto.setAddress(account.getAddress());
        dto.setDob(account.getDob());
        dto.setEmail(account.getEmail());
        dto.setSex(account.getSex());
        dto.setPhone(account.getPhone());
        dto.setStatus(account.getStatus());
        return dto;
    }
}
