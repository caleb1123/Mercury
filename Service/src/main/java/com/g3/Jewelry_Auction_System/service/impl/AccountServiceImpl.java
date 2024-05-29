package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
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
    @Override
    public Account createAccount(AccountDTO accountDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String encodedPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(encodedPassword);

        // Chuyển đổi DTO thành entity
        Account createAccount = accountConverter.toEntity(accountDTO);

        // Lưu vào cơ sở dữ liệu
        accountRepository.save(createAccount);

        return createAccount;
    }
}
