package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.entity.ERole;
import com.g3.Jewelry_Auction_System.entity.Role;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.RoleRepository;
import com.g3.Jewelry_Auction_System.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        String encodedPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(encodedPassword);

        Account createAccount = accountConverter.toEntity(accountDTO);

        Role userRole = roleRepository.findRoleByRoleName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        createAccount.setRole(userRole);

        accountRepository.save(createAccount);

        return accountConverter.toDTO(createAccount);
    }
}
