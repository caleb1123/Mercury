package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.entity.Role;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.request.CreateAccountRequest;
import com.g3.Jewelry_Auction_System.payload.request.SignUpRequest;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.payload.response.AccountSearchByRoleResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.RoleRepository;
import com.g3.Jewelry_Auction_System.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountConverter accountConverter;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder(10);
        Mockito.lenient().when(accountConverter.toResponse(any())).thenReturn(new AccountResponse());

    }

    @Test
    public void testCreateAccountSuccess() {
        // Prepare test data
        CreateAccountRequest request = CreateAccountRequest.builder()
                .userName("testuser")
                .email("test@example.com")
                .phone("123456789")
                .roleId(1)
                .build();
        Account account = new Account();
        Role role = new Role();
        AccountDTO accountDTO = new AccountDTO();

        // Mock behavior
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(accountRepository.findByPhone(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        when(accountConverter.toEntity(any(CreateAccountRequest.class))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountConverter.toDTO(any(Account.class))).thenReturn(accountDTO);

        // Call the method
        AccountDTO result = accountService.createAccount(request);

        // Verify the result
        assertNotNull(result);
        verify(accountRepository).findByEmail(anyString());
        verify(accountRepository).findByPhone(anyString());
        verify(roleRepository).findById(anyInt());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    public void testCreateAccountEmailTaken() {
        // Prepare test data
        CreateAccountRequest request = CreateAccountRequest.builder()
                .userName("testuser")
                .email("test@example.com")
                .phone("123456789")
                .roleId(1)
                .build();

        // Mock behavior
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(new Account()));

        // Call the method and verify exception
        AppException exception = assertThrows(AppException.class, () -> {
            accountService.createAccount(request);
        });
        assertEquals(ErrorCode.EMAIL_TAKEN, exception.getErrorCode());
    }

    @Test
    public void testCreateAccountPhoneTaken() {
        // Prepare test data
        CreateAccountRequest request = CreateAccountRequest.builder()
                .userName("testuser")
                .email("test@example.com")
                .phone("123456789")
                .roleId(1)
                .build();

        // Mock behavior
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(accountRepository.findByPhone(anyString())).thenReturn(Optional.of(new Account()));

        // Call the method and verify exception
        AppException exception = assertThrows(AppException.class, () -> {
            accountService.createAccount(request);
        });
        assertEquals(ErrorCode.PHONE_TAKEN, exception.getErrorCode());
    }
    @Test
    public void testDeactivateAccountSuccess() {
        // Prepare test data
        String userName = "testuser";
        Account account = new Account();
        account.setStatus(true); // Assume the account is active initially

        // Mock behavior
        when(accountRepository.findByUserName(userName)).thenReturn(Optional.of(account));

        // Call the method
        accountService.deactivateAccount(userName);

        // Verify the result
        // Use the getter method to check the status
        assertFalse(account.getStatus()); // Ensure the status is set to false
        verify(accountRepository).save(account); // Verify that save is called
    }


    @Test
    public void testDeactivateAccountUserNotFound() {
        // Prepare test data
        String userName = "testuser";

        // Mock behavior
        when(accountRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // Call the method and verify exception
        AppException exception = assertThrows(AppException.class, () -> {
            accountService.deactivateAccount(userName);
        });
        assertEquals(ErrorCode.USER_NOT_EXISTED, exception.getErrorCode());
    }

    @Test
    public void testGetAccountList() {
        // Prepare test data
        Account account1 = new Account();
        Account account2 = new Account();
        List<Account> accountList = List.of(account1, account2);
        AccountDTO accountDTO1 = new AccountDTO();
        AccountDTO accountDTO2 = new AccountDTO();

        // Mock behavior
        when(accountRepository.findAll()).thenReturn(accountList);
        when(accountConverter.toDTO(account1)).thenReturn(accountDTO1);
        when(accountConverter.toDTO(account2)).thenReturn(accountDTO2);

        // Call the method
        List<AccountDTO> result = accountService.getAccountList();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals(accountDTO2, result.get(0)); // Verify that the list is reversed
        assertEquals(accountDTO1, result.get(1));
    }

    @Test
    public void testGetAccountListEmpty() {
        // Mock behavior
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the method
        List<AccountDTO> result = accountService.getAccountList();

        // Verify the result
        assertTrue(result.isEmpty());
    }


    @Test
    public void testSearchAccountByRoleName() {
        // Prepare test data
        String roleName = "admin";

        // Create an Object array with the structure that the query returns
        Object[] accountArray = {1, "123 Test St", "test@example.com", "Test User", "123456789", true, true, 1};

        // Wrap the Object array in a List of Object arrays
        List<Object[]> accounts = Collections.singletonList(accountArray);

        // Create the expected response object
        AccountSearchByRoleResponse expectedResponse = AccountSearchByRoleResponse.builder()
                .accountId(1)
                .address("123 Test St")
                .email("test@example.com")
                .fullName("Test User")
                .phone("123456789")
                .sex(true)
                .status(true)
                .roleId(1)
                .build();

        // Mock behavior
        when(accountRepository.searchAccountByRoleName(roleName)).thenReturn(accounts);

        // Call the method
        List<AccountSearchByRoleResponse> result = accountService.searchAccountByRoleName(roleName);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResponse, result.get(0));
    }





    @Test
    public void testGetAccountByAccountId() {
        // Prepare test data
        int id = 1;
        Account account = new Account();
        AccountDTO accountDTO = new AccountDTO();

        // Mock behavior
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(accountConverter.toDTO(account)).thenReturn(accountDTO);

        // Call the method
        AccountDTO result = accountService.getAccountByAccountId(id);

        // Verify the result
        assertNotNull(result);
        assertEquals(accountDTO, result);
    }

    @Test
    public void testGetAccountByAccountIdNotFound() {
        // Prepare test data
        int id = 1;

        // Mock behavior
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        // Call the method and verify exception
        AppException exception = assertThrows(AppException.class, () -> {
            accountService.getAccountByAccountId(id);
        });
        assertEquals(ErrorCode.USER_EXISTED, exception.getErrorCode());
    }

    @Test
    public void testGetAccountByUsername() {
        // Prepare test data
        String username = "testuser";
        Account account = new Account();
        AccountDTO accountDTO = new AccountDTO();

        // Mock behavior
        when(accountRepository.findByUserName(username)).thenReturn(Optional.of(account));
        when(accountConverter.toDTO(account)).thenReturn(accountDTO);

        // Call the method
        AccountDTO result = accountService.getAccountByUsername(username);

        // Verify the result
        assertNotNull(result);
        assertEquals(accountDTO, result);
    }

    @Test
    public void testGetAccountByUsernameNotFound() {
        // Prepare test data
        String username = "testuser";

        // Mock behavior
        when(accountRepository.findByUserName(username)).thenReturn(Optional.empty());

        // Call the method and verify exception
        AppException exception = assertThrows(AppException.class, () -> {
            accountService.getAccountByUsername(username);
        });
        assertEquals(ErrorCode.USER_NOT_EXISTED, exception.getErrorCode());
    }
    @Test
    public void testUpdateAccount_EmailTaken() {
        // Prepare test data
        String username = "testuser";
        Account existingAccount = new Account();
        existingAccount.setAccountId(1);
        existingAccount.setEmail("old@example.com");
        existingAccount.setPhone("123456789");

        AccountDTO updateDTO = new AccountDTO();
        updateDTO.setEmail("taken@example.com");

        // Prepare mocks
        Account accountWithSameEmail = new Account();
        accountWithSameEmail.setAccountId(2);

        when(accountRepository.findByUserName(username)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.findByEmail(updateDTO.getEmail())).thenReturn(Optional.of(accountWithSameEmail));

        // Call the method and expect exception
        AppException exception = assertThrows(AppException.class, () -> {
            accountService.updateAccount(updateDTO, username);
        });

        assertEquals(ErrorCode.EMAIL_TAKEN, exception.getErrorCode());
    }

    @Test
    public void testUpdateAccount_InvalidAge() {
        // Prepare test data
        String username = "testuser";
        Account existingAccount = new Account();
        existingAccount.setAccountId(1);
        existingAccount.setEmail("old@example.com");
        existingAccount.setPhone("123456789");

        AccountDTO updateDTO = new AccountDTO();
        updateDTO.setDob(LocalDate.of(1800, 1, 1)); // Invalid age

        // Prepare mocks
        when(accountRepository.findByUserName(username)).thenReturn(Optional.of(existingAccount));

        // Call the method and expect exception
        AppException exception = assertThrows(AppException.class, () -> {
            accountService.updateAccount(updateDTO, username);
        });

        assertEquals(ErrorCode.INVALID_AGE, exception.getErrorCode());
    }
}
