package com.assignment.tinyledger.repository;

import com.assignment.tinyledger.exception.AccountAlreadyExists;
import com.assignment.tinyledger.exception.AccountNotFoundException;
import com.assignment.tinyledger.model.Account;
import com.assignment.tinyledger.model.TransactionType;
import com.assignment.tinyledger.repository.impl.AccountRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryTests {

    private AccountRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new AccountRepositoryImpl();

    }

    @Test
    void testCreateAccount() {
        Account request = Account.builder()
                .accountNumber("GB345698845")
                .accountType("SAVINGS_ACCOUNT")
                .build();
        Account resonse = repository.createAccount(request);

        assertEquals(resonse.getAccountNumber(), request.getAccountNumber());
        assertTrue(BigDecimal.ZERO.compareTo(resonse.getAvailableBalance()) == 0);
    }

    @Test
    void testCheckIfAccountExists() {

        Boolean isAccountExists = repository.checkIfAccountExists("GB345698846");

        assertTrue(isAccountExists);
    }

    @Test
    void testGetAccountBalance() {
        Account request = Account.builder()
                .accountNumber("GB345698849")
                .accountType("SAVINGS_ACCOUNT")
                .build();
        Account resonse = repository.createAccount(request);

        BigDecimal updatedBalance = repository.updateBalance("GB345698849", TransactionType.DEPOSIT, BigDecimal.valueOf(100));
        BigDecimal availableBalance = repository.getAccountBalance("GB345698849");

        assertTrue(availableBalance.compareTo(BigDecimal.valueOf(100)) == 0);
    }

    @Test
    void testUpdateBalance() {
        Account request = Account.builder()
                .accountNumber("GB345698846")
                .accountType("SAVINGS_ACCOUNT")
                .build();
        Account resonse = repository.createAccount(request);
        BigDecimal updatedBalance = repository.updateBalance("GB345698846", TransactionType.DEPOSIT, BigDecimal.valueOf(100));
        BigDecimal availableBalance = repository.updateBalance("GB345698846", TransactionType.WITHDRAWAL, BigDecimal.valueOf(50));
        assertTrue(availableBalance.compareTo(BigDecimal.valueOf(50)) == 0);
    }

    @Test
    void whenAccountNumberDoesNotExists_ThenAccountNotFoundExceptionIsReturned() {

        assertThrows(AccountNotFoundException.class, () -> repository.getAccountBalance("GB3456988467"));
    }
}
