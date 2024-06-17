package com.g3.Jewelry_Auction_System.converter;

import com.g3.Jewelry_Auction_System.entity.ERole;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.payload.request.CreateAccountRequest;
import com.g3.Jewelry_Auction_System.payload.request.SignUpRequest;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.payload.response.WinnerResponse;
import com.g3.Jewelry_Auction_System.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    @Autowired
    RoleConverter roleConverter;
    @Autowired
    RoleRepository roleRepository;
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
        entity.setRole(roleRepository.getReferenceById(accountDTO.getRoleId()));
        return entity;
    }

    public Account toEntity(CreateAccountRequest accountDTO) {
        if (accountDTO == null){
            return null;
        }
        Account entity = new Account();
        entity.setUserName(accountDTO.getUserName());

        entity.setEmail(accountDTO.getEmail());
        entity.setPhone(accountDTO.getPhone());
        entity.setRole(roleRepository.getReferenceById(accountDTO.getRoleId()));
        return entity;
    }

    public Account toEntity(SignUpRequest accountDTO) {
        if (accountDTO == null){
            return null;
        }
        Account entity = new Account();
        entity.setFullName(accountDTO.getFullName());
        entity.setUserName(accountDTO.getUserName());
        entity.setPassword(accountDTO.getPassword());
        entity.setEmail(accountDTO.getEmail());
        entity.setPhone(accountDTO.getPhone());
        entity.setRole(roleRepository.getReferenceById(accountDTO.getRoleId()));
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
        dto.setRoleId(roleConverter.toDTO(account.getRole()).getRoleId());
        return dto;
    }

    public AccountResponse toResponse(Account account) {
        if (account == null){
            return null;
        }

        AccountResponse dto = new AccountResponse();
        dto.setAccountId(account.getAccountId());
        dto.setFullName(account.getFullName());
        dto.setUserName(account.getUserName());
        dto.setAddress(account.getAddress());
        dto.setDob(account.getDob());
        dto.setEmail(account.getEmail());
        dto.setSex(account.getSex());
        dto.setPhone(account.getPhone());
        dto.setStatus(account.getStatus());
        dto.setRoleId(roleConverter.toDTO(account.getRole()).getRoleId());
        return dto;
    }
    public WinnerResponse toWinnerResponse(Account account) {
        if (account == null) {
            return null;
        }
        WinnerResponse dto = new WinnerResponse();
        dto.setWinnerId(account.getAccountId());
        dto.setUsername(account.getUserName());
        return dto;
    }
}
