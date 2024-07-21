package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.payload.request.CreateAccountRequest;
import com.g3.Jewelry_Auction_System.payload.request.SignUpRequest;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.payload.response.AccountSearchByRoleResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @CrossOrigin(origins = "http://localhost:3001")
    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountRequest accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @PutMapping("/deactivate/{userName}")
    public ResponseEntity<Account> deactivateAccount(@PathVariable String userName) {
            accountService.deactivateAccount(userName);
            return ResponseEntity.ok().build(); // Return 200 OK on successful deactivation
    }
    @CrossOrigin(origins = "http://localhost:3001")
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



    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/myinfor")
    public ResponseEntity<AccountResponse> getMyInfo() {
        AccountResponse accountResponse = accountService.getMyInfor();
        return ResponseEntity.ok(accountResponse);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/search/{name}")
    public ResponseEntity<List<AccountResponse>> searchAccountByName(@PathVariable String name) {
        List<AccountResponse> accounts = accountService.searchAccountByName(name);
        return ResponseEntity.ok(accounts);
    }
    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/searchByRole")
    public ResponseEntity<List<AccountSearchByRoleResponse>> searchAccountByRoleName(@RequestParam String roleName) {
        List<AccountSearchByRoleResponse> accounts = accountService.searchAccountByRoleName(roleName);
        return ResponseEntity.ok(accounts);
    }

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping("/{username}")
    public ResponseEntity<AccountDTO> getAccountByUsername(@PathVariable String username) {
        AccountDTO accountDTO = accountService.getAccountByUsername(username);
        return ResponseEntity.ok(accountDTO);
    }
    @GetMapping("/test")
    public ResponseEntity<SecurityContext> getContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        return ResponseEntity.ok(context);
    }
    @CrossOrigin(origins = "http://localhost:3001")

    @GetMapping("/id/{id}")
    public ResponseEntity<AccountDTO> getAccountByUsername(@PathVariable int id) {
        AccountDTO accountDTO = accountService.getAccountByAccountId(id);
        return ResponseEntity.ok(accountDTO);
    }

}
