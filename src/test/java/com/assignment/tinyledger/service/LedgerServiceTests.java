package com.assignment.tinyledger.service;

import com.assignment.tinyledger.exception.AccountNotFoundException;
import com.assignment.tinyledger.exception.InSufficientFundException;
import com.assignment.tinyledger.model.Transaction;
import com.assignment.tinyledger.model.TransactionRequest;
import com.assignment.tinyledger.model.TransactionResponse;
import com.assignment.tinyledger.model.TransactionType;
import com.assignment.tinyledger.repository.AccountTransactionRepository;
import com.assignment.tinyledger.service.impl.LedgerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LedgerServiceTests {

    @Mock
    private AccountTransactionRepository repository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private LedgerServiceImpl ledgerService;

    @Test
    void testRecordTransactionOfDeposit() {
        TransactionRequest request = TransactionRequest.builder()
                .transactionType(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(2000))
                .transactionDetails("HSBC BANK").build();

        given(accountService.checkIfAccountExists("GB345698845")).willReturn(Boolean.TRUE);
        given(accountService.updateBalance("GB345698845", request.getTransactionType(), request.getAmount())).willReturn(BigDecimal.valueOf(2000));

        TransactionResponse response = ledgerService.recordTransaction("GB345698845", request);

        assertEquals(response.availableBalance(), BigDecimal.valueOf(2000));
    }

    @Test
    void testRecordTransactionOfWithdrawal() {
        TransactionRequest request = TransactionRequest.builder()
                .transactionType(TransactionType.WITHDRAWAL)
                .amount(BigDecimal.valueOf(100))
                .transactionDetails("HSBC BANK").build();

        given(accountService.checkIfAccountExists("GB345698845")).willReturn(Boolean.TRUE);
        given(accountService.getAccountBalance("GB345698845")).willReturn(BigDecimal.valueOf(2000));
        given(accountService.updateBalance("GB345698845", request.getTransactionType(), request.getAmount())).willReturn(BigDecimal.valueOf(1900));

        TransactionResponse response = ledgerService.recordTransaction("GB345698845", request);

        assertEquals(response.availableBalance(), BigDecimal.valueOf(1900));
    }

    @Test
    void shouldReturnInsufficientFunds_WhenWithdrawalAmountIsGreaterThanAvailableBalance() {
        TransactionRequest request = TransactionRequest.builder()
                .transactionType(TransactionType.WITHDRAWAL)
                .amount(BigDecimal.valueOf(100))
                .transactionDetails("HSBC BANK").build();

        given(accountService.checkIfAccountExists("GB345698845")).willReturn(Boolean.TRUE);
        given(accountService.getAccountBalance("GB345698845")).willReturn(BigDecimal.valueOf(50));
        assertThrows(InSufficientFundException.class, () -> {
            ledgerService.recordTransaction("GB345698845", request);
        });
    }

    @Test
    void testAccountNotExistsWhenRecordTransactions() {
        TransactionRequest request = TransactionRequest.builder()
                .transactionType(TransactionType.WITHDRAWAL)
                .amount(BigDecimal.valueOf(100))
                .transactionDetails("HSBC BANK").build();

        given(accountService.checkIfAccountExists("GB345698845")).willReturn(Boolean.FALSE);

        assertThrows(AccountNotFoundException.class, () -> {
            ledgerService.recordTransaction("GB345698845", request);
        });
    }

    @Test
    void testGetAllTransactionsByAccountNumber() {

        Transaction deposit = Transaction.builder().transactionId("26ba4fd0-a719-41d1-8951-b54bf91e8b8f").transactionType(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(1000.300)).availableBalance(BigDecimal.valueOf(1000.300)).transactionDate(LocalDateTime.now().minusHours(4)).build();

        Transaction withdrawal = Transaction.builder().transactionId("26ba4fd0-a719-41d1-8951-b54bf91e8b9f").transactionType(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(500)).availableBalance(BigDecimal.valueOf(500)).transactionDate(LocalDateTime.now().minusHours(4)).build();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(deposit);
        transactionList.add(withdrawal);

        given(accountService.checkIfAccountExists("GB345698845")).willReturn(Boolean.TRUE);
        given(repository.getAllTransactionsByAccountNumber("GB345698845")).willReturn(transactionList);

        List<Transaction> responseList = ledgerService.getAllTransactions("GB345698845");
        org.junit.jupiter.api.Assertions.assertAll(() ->
                        assertTrue(responseList.stream().anyMatch(transaction -> transaction.getTransactionId().equals("26ba4fd0-a719-41d1-8951-b54bf91e8b8f"))),
                () -> assertTrue(responseList.stream().anyMatch(transaction -> transaction.getTransactionId().equals("26ba4fd0-a719-41d1-8951-b54bf91e8b9f"))));
        ;
    }
}
