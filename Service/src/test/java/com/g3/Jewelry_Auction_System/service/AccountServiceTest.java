package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.request.CreateAccountRequest;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {
        Account account = new Account();
        account.setAccountId(1);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        CreateAccountRequest request = new CreateAccountRequest("testuser", "test@example.com", "0123456789", 2);
        AccountDTO createdAccount = accountService.createAccount(request);

        assertNotNull(createdAccount);
        assertEquals(1, createdAccount.getAccountId());
    }

//    @Test
//    public void testCreateAccount_Exception() {
//        when(accountRepository.save(any(Account.class))).thenThrow(new RuntimeException("Some error occurred"));
//
//        CreateAccountRequest request = new CreateAccountRequest("testuser", "password123", "test@example.com", "1234567890");
//
//        assertThrows(RuntimeException.class, () -> {
//            accountService.createAccount(request);
//        });
//    }
//
//    @Test
//    public void testDeactivateAccount_Success() {
//        Account account = new Account();
//        account.setUsername("testuser");
//
//        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.of(account));
//        doNothing().when(accountRepository).delete(account);
//
//        accountService.deactivateAccount("testuser");
//
//        verify(accountRepository, times(1)).delete(account);
//    }
//
//    @Test
//    public void testUpdateAccount_Success() {
//        Account account = new Account();
//        account.setUsername("testuser");
//
//        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.of(account));
//        when(accountRepository.save(any(Account.class))).thenReturn(account);
//
//        AccountDTO accountDto = new AccountDTO();
//        accountService.updateAccount(accountDto, "testuser");
//
//        verify(accountRepository, times(1)).save(account);
//    }
//
//    @Test
//    public void testGetAccountList_Success() {
//        List<Account> accountList = Collections.emptyList();
//        when(accountRepository.findAll()).thenReturn(accountList);
//
//        List<AccountDTO> accountDTOList = accountService.getAccountList();
//
//        assertNotNull(accountDTOList);
//        assertEquals(0, accountDTOList.size());
//    }
//
//    @Test
//    public void testGetMyInfor_Success() {
//        Account account = new Account();
//        AccountResponse accountResponse = new AccountResponse();
//
//        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.of(account));
//
//        AccountResponse myInfor = accountService.getMyInfor();
//
//        assertNotNull(myInfor);
//    }
//
//    @Test
//    public void testSearchAccountByName_Success() {
//        Account account = new Account();
//        when(accountRepository.findByUsername("testuser")).thenReturn(Optional.of(account));
//
//        AccountResponse accountResponse = accountService.searchAccountByName("testuser");
//
//        assertNotNull(accountResponse);
//    }
//
//    @Test
//    public void testSearchAccountByRole_Success() {
//        List<Account> accountList = Collections.emptyList();
//        when(accountRepository.findByRole("admin")).thenReturn(accountList);
//
//        List<AccountSearchByRoleResponse> responseList = accountService.searchAccountByRole("admin");
//
//        assertNotNull(responseList);
//        assertEquals(0, responseList.size());
//    }
}
