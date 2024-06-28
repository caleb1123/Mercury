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

import java.util.ArrayList;
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
        Optional<Account> existingUserEmail = accountRepository.findByEmail(createAccountRequest.getEmail());
        Optional<Account> existingUserPhone = accountRepository.findByPhone(createAccountRequest.getPhone());
        if (existingUserEmail.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);
        }
        if (existingUserPhone.isPresent()) {
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
        if (!updateDTO.getPassword().isEmpty()) {
            user.setPassword(encodedPassword);
        }
        if (!updateDTO.getEmail().isEmpty()) {
            user.setEmail(updateDTO.getEmail());
        }
        if (!updateDTO.getAddress().isEmpty()) {
            user.setAddress(updateDTO.getAddress());
        }
        if (!updateDTO.getFullName().isEmpty()) {
            user.setFullName(updateDTO.getFullName());
        }
        if (!updateDTO.getPhone().isEmpty()) {
            user.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getSex() != null) {
            user.setSex(updateDTO.getSex());
        }
        if (updateDTO.getDob() != null) {
            user.setDob(updateDTO.getDob());
        }
        if (updateDTO.getStatus() != null) {
            user.setStatus(updateDTO.getStatus());
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

    private AccountSearchByRoleResponse convertToAccountSearchByRoleResponse(Object[] account) {
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
    public AccountDTO createAccountByUser(SignUpRequest signUpRequest) {
        Optional<Account> existingUserEmail = accountRepository.findByEmail(signUpRequest.getEmail());
        Optional<Account> existingUserPhone = accountRepository.findByPhone(signUpRequest.getPhone());
        if (existingUserEmail.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_TAKEN);
        }
        if (existingUserPhone.isPresent()) {
            throw new AppException(ErrorCode.PHONE_TAKEN);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodedPassword);

        Account createAccount = accountConverter.toEntity(signUpRequest);

        Role userRole = roleRepository.findById(4)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        createAccount.setRole(userRole);
        createAccount.setStatus(true);

        accountRepository.save(createAccount);

        return accountConverter.toDTO(createAccount);
    }
    @Override
    public AccountDTO getAccountByUsername(String username) {
        Account user = accountRepository
                .findByUserName(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return accountConverter.toDTO(user);
    }
}
