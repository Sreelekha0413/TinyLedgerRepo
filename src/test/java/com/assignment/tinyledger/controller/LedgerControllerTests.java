package com.assignment.tinyledger.controller;

import com.assignment.tinyledger.model.Transaction;
import com.assignment.tinyledger.model.TransactionRequest;
import com.assignment.tinyledger.model.TransactionResponse;
import com.assignment.tinyledger.model.TransactionType;
import com.assignment.tinyledger.service.LedgerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LedgerController.class)
public class LedgerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LedgerService ledgerService;

    @Test
    void testCreateTransactionOfDeposit() throws Exception {

        TransactionRequest request = TransactionRequest.builder().amount(BigDecimal.valueOf(1000.300)).transactionType(TransactionType.DEPOSIT).transactionDetails("HSBC BANK").build();
        TransactionResponse response = new TransactionResponse("26ba4fd0-a719-41d1-8951-b54bf91e8b8f", BigDecimal.valueOf(1000.300));
        given(ledgerService.recordTransaction("GB34569881", request)).willReturn(response);

        mockMvc.perform(post("/api/ledger/{accountNumber}/transactions", "GB34569881")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("26ba4fd0-a719-41d1-8951-b54bf91e8b8f"))
                .andExpect(jsonPath("$.availableBalance").value(1000.300));
    }

    @Test
    void testCreateTransactionOfWithdrawal() throws Exception {

        TransactionRequest request = TransactionRequest.builder().amount(BigDecimal.valueOf(500.300)).transactionType(TransactionType.WITHDRAWAL).transactionDetails("ATM").build();
        TransactionResponse response = new TransactionResponse("26ba4fd0-a719-41d1-8951-b54bf91e8b9f", BigDecimal.valueOf(500));
        given(ledgerService.recordTransaction("GB34569881", request)).willReturn(response);

        mockMvc.perform(post("/api/ledger/{accountNumber}/transactions", "GB34569881")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("26ba4fd0-a719-41d1-8951-b54bf91e8b9f"))
                .andExpect(jsonPath("$.availableBalance").value(500));
    }

    @Test
    void testGetAllTransactionOfAccountNumber() throws Exception {

        Transaction deposit = Transaction.builder().transactionId("26ba4fd0-a719-41d1-8951-b54bf91e8b8f").transactionType(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(1000.300)).availableBalance(BigDecimal.valueOf(1000.300)).transactionDate(LocalDateTime.now().minusHours(4)).build();

        Transaction withdrawal = Transaction.builder().transactionId("26ba4fd0-a719-41d1-8951-b54bf91e8b9f").transactionType(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500)).availableBalance(BigDecimal.valueOf(500)).transactionDate(LocalDateTime.now().minusHours(4)).build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(deposit);
        transactionList.add(withdrawal);
        given(ledgerService.getAllTransactions("GB34569881")).willReturn(transactionList);

        MvcResult result = mockMvc.perform(get("/api/ledger/{accountNumber}/transactions", "GB34569881"))
                .andExpect(status().isOk())
                .andReturn();
        org.junit.jupiter.api.Assertions.assertAll(() -> assertTrue(result.getResponse().getContentAsString().contains("26ba4fd0-a719-41d1-8951-b54bf91e8b8f")),
                () -> assertTrue(result.getResponse().getContentAsString().contains("1000.3")),
                () -> assertTrue(result.getResponse().getContentAsString().contains("26ba4fd0-a719-41d1-8951-b54bf91e8b9f")),
                () -> assertTrue(result.getResponse().getContentAsString().contains("500")));
    }
}
