package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.entity.ERole;
import com.g3.Jewelry_Auction_System.entity.Role;
import com.g3.Jewelry_Auction_System.entity.*;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.payload.request.UpdatePasswordRequest;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.RoleRepository;
import com.g3.Jewelry_Auction_System.repository.AuctionRepository;
import com.g3.Jewelry_Auction_System.service.AccountService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    RoleRepository roleRepository;
    private AuctionRepository auctionRepository;
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String encodedPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(encodedPassword);

        Account createAccount = accountConverter.toEntity(accountDTO);

        Role userRole = roleRepository.findRoleByRoleName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        createAccount.setRole(userRole);

        accountRepository.save(createAccount);

        return accountConverter.toDTO(createAccount);
    }

    @Override
    public void deactivateAccount(String userName) {
        Account user = accountRepository
                .findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setStatus(false);
        accountRepository.save(user);
    }

    @Override
    public void updateAccount(AccountDTO updateDTO, String username) {
        if (!updateDTO.getUserName().equals(username)) {
            throw new RuntimeException("Username does not match request");
        }
        Optional<Account> existingUserEmail = accountRepository.findByEmail(updateDTO.getEmail());
        Optional<Account> existingUserPhone = accountRepository.findByPhone(updateDTO.getPhone());
        Account user = accountRepository
                .findByUserName(updateDTO.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(updateDTO.getPassword());
        if (existingUserEmail.isPresent() && user.getAccountId() != existingUserEmail.get().getAccountId()) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);
        }
        if (existingUserPhone.isPresent() && user.getAccountId() != existingUserPhone.get().getAccountId()) {
            throw new AppException(ErrorCode.PHONE_TAKEN);
        }
        if (updateDTO.getPassword().isEmpty()
                || updateDTO.getEmail().isEmpty()
                || updateDTO.getAddress().isEmpty()
                || updateDTO.getFullName().isEmpty()
                || updateDTO.getPhone().isEmpty()) {
            throw new AppException(ErrorCode.EMPTY_FIELD);
        } else {
            user.setPassword(encodedPassword);
            user.setEmail(updateDTO.getEmail());
            user.setAddress(updateDTO.getAddress());
            user.setFullName(updateDTO.getFullName());
            user.setPhone(updateDTO.getPhone());
            user.setDob(updateDTO.getDob());
            user.setSex(updateDTO.getSex());
        }
        //Assuming Sex and DoB can be selected with a dropbox, they shouldn't be empty
        accountRepository.save(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<AccountDTO> getAccountList() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (Account account : accountList) {
            AccountDTO accountDTO = accountConverter.toDTO(account);
            accountDTOList.add(accountDTO);
        }
        return accountDTOList;
    }

    @Override
    public AccountResponse getMyInfor() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Account byUserName = accountRepository.findByUserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        AccountResponse accountResponse = accountConverter.toResponse(byUserName);
        return accountResponse;
    }




}
