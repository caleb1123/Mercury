package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.payload.request.AuthenticationRequest;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/deactivate/{userName}")
    public ResponseEntity<Account> deactivateAccount(@PathVariable String userName) {
            accountService.deactivateAccount(userName);
            return ResponseEntity.ok().build(); // Return 200 OK on successful deactivation
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable String userName) {
            accountService.updateAccount(accountDTO, userName);
            return ResponseEntity.ok().build(); // Return 200 OK on successful update
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/list")
    public ResponseEntity<List<AccountDTO>> getAccountList() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}",authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        List<AccountDTO> accountList = accountService.getAccountList();
        if (accountList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(accountList, HttpStatus.OK);
        }
    }

    @GetMapping("/myinfor")
    public ResponseEntity<AccountResponse> getMyInfo() {
        AccountResponse accountResponse = accountService.getMyInfor();
        return ResponseEntity.ok(accountResponse);
    }

    @PostMapping("/ok")
    public String ok(@RequestBody AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        var account = accountRepository.findByUserName(request.getUserName()).orElseThrow(
                () -> new AppException(ErrorCode.USERNAME_INVALID)
        );

        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.PASSWORD_NOT_CORRECT);
        }

        return "OK";
    }
}
