package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/deactivate/{userName}")
    public ResponseEntity<Account> deactivateAccount(@PathVariable String userName) {
        try {
            accountService.deactivateAccount(userName);
            return ResponseEntity.ok().build(); // Return 200 OK on successful deletion
        } catch (Exception e) {
            // Handle other potential exceptions (e.g., database issues)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 Internal Server Error
        }
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDTO accountDTO) {
        try {
            accountService.updateAccount(accountDTO);
            return ResponseEntity.ok().build(); // Return 200 OK on successful deletion
        } catch (Exception e) {
            // Handle other potential exceptions (e.g., database issues)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 Internal Server Error
        }
    }

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
}
