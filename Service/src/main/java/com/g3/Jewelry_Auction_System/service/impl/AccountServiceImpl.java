package com.g3.Jewelry_Auction_System.service.impl;

import com.g3.Jewelry_Auction_System.entity.Role;
import com.g3.Jewelry_Auction_System.entity.*;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.payload.request.CreateAccountRequest;
import com.g3.Jewelry_Auction_System.payload.request.SignUpRequest;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.payload.response.AccountSearchByRoleResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.RoleRepository;
import com.g3.Jewelry_Auction_System.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    RoleRepository roleRepository;


    //@PreAuthorize("hasRole('ADMIN')")
    @Override
    public AccountDTO createAccount(CreateAccountRequest createAccountRequest) {
        Account existingUserEmail = accountRepository.findByEmail(createAccountRequest.getEmail()).orElse(null);
        Account existingUserPhone = accountRepository.findByPhone(createAccountRequest.getPhone()).orElse(null);
        if (existingUserEmail != null) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);
        }
        if (existingUserPhone != null) {
            throw new AppException(ErrorCode.PHONE_TAKEN);
        }
        Account createAccount = accountConverter.toEntity(createAccountRequest);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        createAccount.setPassword("12345678");
        String encodedPassword = passwordEncoder.encode(createAccount.getPassword());
        createAccount.setPassword(encodedPassword);


        Role userRole = roleRepository.findById(createAccountRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        createAccount.setRole(userRole);
        createAccount.setStatus(false);

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
    public Account updateAccount(AccountDTO updateDTO, String username) {

        Account user = accountRepository
                .findByUserName(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (updateDTO.getPassword() !=  null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            String encodedPassword = passwordEncoder.encode(updateDTO.getPassword());
            user.setPassword(encodedPassword);
        }
        if (updateDTO.getEmail() != null) {
            Account userWithSameEmail = accountRepository.findByEmail(updateDTO.getEmail()).orElse(null);
            if (userWithSameEmail != null && user.getAccountId() != userWithSameEmail.getAccountId()) {
                throw new AppException(ErrorCode.EMAIL_TAKEN);
            } else {
                user.setEmail(updateDTO.getEmail());
            }
        }
        if (updateDTO.getPhone() != null) {
            Account userWithSamePhone = accountRepository.findByPhone(updateDTO.getPhone()).orElse(null);
            if (userWithSamePhone != null && user.getAccountId() != userWithSamePhone.getAccountId()) {
                throw new AppException(ErrorCode.PHONE_TAKEN);
            } else {
                user.setPhone(updateDTO.getPhone());
            }
        }
        if (updateDTO.getAddress() != null) {
            user.setAddress(updateDTO.getAddress());
        }
        if (updateDTO.getFullName() != null) {
            user.setFullName(updateDTO.getFullName());
        }
        if (updateDTO.getSex() != null) {
            user.setSex(updateDTO.getSex());
        }
        if (updateDTO.getDob() != null) {
            long age = ChronoUnit.YEARS.between(updateDTO.getDob(), LocalDateTime.now());
            if (age < 18 || age > 150) {
                throw new AppException(ErrorCode.INVALID_AGE);
            } else {
                user.setDob(updateDTO.getDob());
            }
        }
        if (updateDTO.getStatus() != null) {
            user.setStatus(updateDTO.getStatus());
        }
        accountRepository.save(user);
        return user;
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
        Collections.reverse(accountDTOList);
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

    @Override
    public List<AccountResponse> searchAccountByName(String name) {
        List<Account> accounts = accountRepository.searchAccountByName(name);
        return accounts.stream()
                .map(this::convertToAccountResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountSearchByRoleResponse> searchAccountByRoleName(String roleName) {
        List<Object[]> accounts = accountRepository.searchAccountByRoleName(roleName);
        return accounts.stream()
                .map(this::convertToAccountSearchByRoleResponse)
                .collect(Collectors.toList());
    }

    private AccountResponse convertToAccountResponse(Account account) {
        if (account.getRole() == null) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND); // Hoặc một mã lỗi phù hợp
        }

        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .fullName(account.getFullName())
                .userName(account.getUserName())
                .address(account.getAddress())
                .dob(account.getDob()) // Assuming dob is a LocalDate in Account entity
                .email(account.getEmail())
                .sex(account.getSex())
                .phone(account.getPhone())
                .status(account.getStatus())
                .roleId(account.getRole().getRoleId())
                .build();
    }

    public AccountSearchByRoleResponse convertToAccountSearchByRoleResponse(Object[] account) {

        return AccountSearchByRoleResponse.builder()
                .accountId((Integer) account[0])
                .address((String) account[1])
                .email((String) account[2])
                .fullName((String) account[3])
                .phone((String) account[4])
                .sex((Boolean) account[5])
                .status((Boolean) account[6])
                .roleId((Integer) account[7])
                .build();
    }

    @Override
    public AccountDTO getAccountByAccountId(int id) {
       Account user = accountRepository.findById(id)
               .orElseThrow(()-> new AppException(ErrorCode.USER_EXISTED));
        return accountConverter.toDTO(user);
    }

    @Override
    public AccountDTO getAccountByUsername(String username) {
        Account user = accountRepository
                .findByUserName(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return accountConverter.toDTO(user);
    }
}
