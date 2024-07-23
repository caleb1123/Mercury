package com.g3.Jewelry_Auction_System.service;

import com.g3.Jewelry_Auction_System.converter.AccountConverter;
import com.g3.Jewelry_Auction_System.entity.Account;
import com.g3.Jewelry_Auction_System.exception.AppException;
import com.g3.Jewelry_Auction_System.exception.ErrorCode;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.repository.AccountRepository;
import com.g3.Jewelry_Auction_System.repository.RoleRepository;
import com.g3.Jewelry_Auction_System.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTestByMyInfor {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountConverter accountConverter;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AccountServiceImpl accountService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
    @Test
    public void testGetMyInforSuccess() {
        // Prepare test data
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = new Account();
        AccountResponse accountResponse = new AccountResponse();

        // Mock behavior
        when(accountRepository.findByUserName(userName)).thenReturn(Optional.of(account));
        when(accountConverter.toResponse(account)).thenReturn(accountResponse);

        // Call the method
        AccountResponse result = accountService.getMyInfor();

        // Verify the result
        assertNotNull(result);
        assertEquals(accountResponse, result);
    }

    @Test
    public void testGetMyInforUserNotFound() {
        // Prepare test data
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        // Mock behavior
        when(accountRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // Call the method and verify exception
        AppException exception = assertThrows(AppException.class, () -> {
            accountService.getMyInfor();
        });
        assertEquals(ErrorCode.USER_NOT_EXISTED, exception.getErrorCode());
    }
}
