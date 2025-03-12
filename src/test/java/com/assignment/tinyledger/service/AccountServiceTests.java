package com.assignment.tinyledger.service;

import com.assignment.tinyledger.exception.AccountAlreadyExists;
import com.assignment.tinyledger.model.Account;
import com.assignment.tinyledger.model.TransactionType;
import com.assignment.tinyledger.repository.AccountRepository;
import com.assignment.tinyledger.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = Account.builder().accountNumber("GB345698845")
                .accountType("SAVINGS_ACCOUNT").build();
    }

    @Test
    public void testCreateAccount() {
        Account response = Account.builder().accountNumber("GB345698845").accountType("SAVINGS_ACCOUNT").availableBalance(BigDecimal.ZERO).build();
        given(repository.createAccount(account)).willReturn(response);

        Account accountResponse = accountService.createAccount(account);
        assertThat(accountResponse).isNotNull();
        ;
    }

    @Test
    public void shouldThrowAccountAlreadyExistsWhenCreateAccount() {
        Account response = Account.builder().accountNumber("GB345698845").accountType("SAVINGS_ACCOUNT").availableBalance(BigDecimal.ZERO).build();
        given(repository.createAccount(account)).willThrow(new AccountAlreadyExists());

        assertThrows(AccountAlreadyExists.class, () -> {
            accountService.createAccount(account);
        });
    }

    @Test
    public void tetGetAccountBalance() {
        given(repository.getAccountBalance("GB345698845")).willReturn(BigDecimal.valueOf(100));
        BigDecimal availableBalance = accountService.getAccountBalance("GB345698845");
        assertEquals(availableBalance, BigDecimal.valueOf(100));
    }

    @Test
    public void tetCheckIfAccountExists() {
        given(repository.checkIfAccountExists("GB345698845")).willReturn(Boolean.TRUE);
        Boolean isAccountExists = accountService.checkIfAccountExists("GB345698845");
        assertEquals(isAccountExists, Boolean.TRUE);
    }

    @Test
    public void tetUpdateAccountBalance() {
        given(repository.updateBalance("GB345698845", TransactionType.DEPOSIT, BigDecimal.valueOf(120))).willReturn(BigDecimal.valueOf(120));
        BigDecimal availableBalance = accountService.updateBalance("GB345698845", TransactionType.DEPOSIT, BigDecimal.valueOf(120));
        assertEquals(availableBalance, BigDecimal.valueOf(120));
    }
}
