package com.g3.Jewelry_Auction_System.controller;

import com.g3.Jewelry_Auction_System.payload.DTO.AccountDTO;
import com.g3.Jewelry_Auction_System.payload.request.CreateAccountRequest;
import com.g3.Jewelry_Auction_System.payload.request.SignUpRequest;
import com.g3.Jewelry_Auction_System.payload.response.AccountResponse;
import com.g3.Jewelry_Auction_System.payload.response.AccountSearchByRoleResponse;
import com.g3.Jewelry_Auction_System.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testCreateAccount() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        AccountDTO response = new AccountDTO();

        when(accountService.createAccount(any(CreateAccountRequest.class))).thenReturn(response);

        mockMvc.perform(post("/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"testuser\", \"password\": \"password123\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{ \"username\": \"testuser\" }"));

        verify(accountService, times(1)).createAccount(any(CreateAccountRequest.class));
    }

    @Test
    void testDeactivateAccount() throws Exception {
        mockMvc.perform(put("/account/deactivate/testuser"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).deactivateAccount("testuser");
    }

    @Test
    void testUpdateAccount() throws Exception {
        AccountDTO request = new AccountDTO();

        mockMvc.perform(put("/account/update/testuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"testuser\", \"password\": \"newpassword123\" }"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).updateAccount(any(AccountDTO.class), eq("testuser"));
    }

    @Test
    void testGetAccountList() throws Exception {
        when(accountService.getAccountList()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/account/list"))
                .andExpect(status().isNoContent());

        verify(accountService, times(1)).getAccountList();
    }

    @Test
    void testGetMyInfo() throws Exception {
        AccountResponse response = new AccountResponse();

        when(accountService.getMyInfor()).thenReturn(response);

        mockMvc.perform(get("/account/myinfor"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getMyInfor();
    }

    @Test
    void testSearchAccountByName() throws Exception {
        when(accountService.searchAccountByName("testuser")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/account/search")
                        .param("name", "testuser"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).searchAccountByName("testuser");
    }

    @Test
    void testSearchAccountByRoleName() throws Exception {
        when(accountService.searchAccountByRoleName("admin")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/account/searchByRole")
                        .param("roleName", "admin"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).searchAccountByRoleName("admin");
    }

    @Test
    void testSignUp() throws Exception {
        SignUpRequest request = new SignUpRequest();
        AccountDTO response = new AccountDTO();

        when(accountService.createAccountByUser(any(SignUpRequest.class))).thenReturn(response);

        mockMvc.perform(post("/account/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"testuser\", \"password\": \"password123\" }"))
                .andExpect(status().isCreated());

        verify(accountService, times(1)).createAccountByUser(any(SignUpRequest.class));
    }

    @Test
    void testGetAccountByUsername() throws Exception {
        AccountDTO response = new AccountDTO();

        when(accountService.getAccountByUsername("testuser")).thenReturn(response);

        mockMvc.perform(get("/account/testuser"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getAccountByUsername("testuser");
    }

    @Test
    void testGetAccountById() throws Exception {
        AccountDTO response = new AccountDTO();

        when(accountService.getAccountByAccountId(1)).thenReturn(response);

        mockMvc.perform(get("/account/id/1"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getAccountByAccountId(1);
    }
}
