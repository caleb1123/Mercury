package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public void deactivateAccount(String userName) {
        Account user = accountRepository
                .findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        accountRepository.deactivateByUserName(userName);
    }

    @Override
    public Account updateAccount(AccountDTO accountDTO) {
        String userName = accountDTO.getUserName();
        Optional<Account> existingAccount = accountRepository.findByEmail(accountDTO.getEmail());
        AccountDTO beforeUpdate = accountConverter.toDTO(accountRepository
                .findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
        if (!beforeUpdate.getPassword().equals(accountDTO.getPassword()) && !accountDTO.getPassword().isEmpty()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            String encodedPassword = passwordEncoder.encode(accountDTO.getPassword());
            accountDTO.setPassword(encodedPassword);
        }
        if (existingAccount.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);
        }
        if (accountDTO.getAddress().isEmpty() || accountDTO.getEmail().isEmpty() || accountDTO.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.EMPTY_FIELD);
        }
        Account updateAccount = accountConverter.toEntity(accountDTO);
        accountRepository.save(updateAccount);
        return updateAccount;
    }
}
