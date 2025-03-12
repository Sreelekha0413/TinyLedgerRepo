package com.assignment.tinyledger.controller;

import com.assignment.tinyledger.exception.AccountAlreadyExists;
import com.assignment.tinyledger.exception.AccountNotFoundException;
import com.assignment.tinyledger.model.Account;
import com.assignment.tinyledger.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTests {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AccountService accountService;

    @Test
    void testCreateAccount() throws Exception {
        Account accountRequest = Account.builder().accountNumber("GB34569881").accountType("SAVINGS_ACCOUNT").build();

        Account response = Account.builder().accountNumber("GB34569881").accountType("SAVINGS_ACCOUNT").availableBalance(BigDecimal.ZERO).build();

        given(accountService.createAccount(accountRequest)).willReturn(response);
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value("GB34569881"))
                .andExpect(jsonPath("$.availableBalance").value(0));
    }

    @Test
    void whenAccountAlreadyExists_AccountAlreadyExistsExceptionIsExpected() throws Exception {
        Account accountRequest = Account.builder().accountNumber("GB34569881").accountType("SAVINGS_ACCOUNT").build();

        given(accountService.createAccount(accountRequest)).willThrow(new AccountAlreadyExists());
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Account Already Exists"));
    }

    @Test
    void testGetAccountBalance() throws Exception {
        Account accountRequest = Account.builder().accountNumber("GB34569881").accountType("SAVINGS_ACCOUNT").build();

        Account response = Account.builder().accountNumber("GB34569881").accountType("SAVINGS_ACCOUNT").availableBalance(BigDecimal.ZERO).build();

        given(accountService.getAccountBalance("GB34569881")).willReturn(BigDecimal.ZERO);
        MvcResult result = mockMvc.perform(get("/api/accounts/{accountId}/balance", "GB34569881"))
                .andExpect(status().isOk())
                .andReturn();
        String availableBalance = result.getResponse().getContentAsString();
        Assertions.assertThat(availableBalance.equals("0"));
    }

    @Test
    void whenAccountDoesNotExists_AccountNotFoundExceptionIsExpected() throws Exception {
        given(accountService.getAccountBalance("GB34569885")).willThrow(new AccountNotFoundException());

        mockMvc.perform(get("/api/accounts/{accountId}/balance", "GB34569885"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Account Doesn't Exists"));

    }
}
