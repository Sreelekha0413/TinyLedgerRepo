package com.assignment.tinyledger.repository;

import com.assignment.tinyledger.model.Transaction;
import com.assignment.tinyledger.model.TransactionType;
import com.assignment.tinyledger.repository.impl.InMemoryAccountTransactionRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTransactionRepositoryTests {

    private InMemoryAccountTransactionRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAccountTransactionRepositoryImpl();

        Transaction deposit = Transaction.builder()
                .transactionId("26ba4fd0-a719-41d1-8951-b54bf91e8b8f")
                .transactionType(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(100))
                .transactionDate(LocalDateTime.now())
                .availableBalance(BigDecimal.valueOf(100))
                .build();
        repository.createTransaction("GB345698846", deposit);
        Transaction withdrawal = Transaction.builder()
                .transactionId("26ba4fd0-a719-41d1-8951-b54bf91e8b9f")
                .transactionType(TransactionType.WITHDRAWAL)
                .amount(BigDecimal.valueOf(50))
                .transactionDate(LocalDateTime.now())
                .availableBalance(BigDecimal.valueOf(50))
                .build();
        repository.createTransaction("GB345698846", withdrawal);
    }

    @Test
    void testCreateTransaction() {
        Transaction request = Transaction.builder()
                .transactionId("26ba4fd0-a719-41d1-8951-b54bf91e8b8f")
                .transactionType(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(100))
                .transactionDate(LocalDateTime.now())
                .availableBalance(BigDecimal.valueOf(100))
                .build();

        repository.createTransaction("GB345698845", request);
        List<Transaction> transactionList = repository.getAllTransactionsByAccountNumber("GB345698845");
        assertTrue(transactionList.stream().anyMatch(transaction -> transaction.getTransactionId().equals(transaction.getTransactionId())));

    }

    @Test
    void testGetAllTransactionsByAccountNumber() {

        List<Transaction> transactionList = repository.getAllTransactionsByAccountNumber("GB345698846");
        assertAll(() -> assertTrue(transactionList.stream().anyMatch(transaction -> transaction.getTransactionId().equals(transaction.getTransactionId()))),
                () -> assertTrue(transactionList.stream().anyMatch(transaction -> transaction.getTransactionId().equals(transaction.getTransactionId()))));

    }
}
