package com.assignment.tinyledger.service.impl;

import com.assignment.tinyledger.model.Account;
import com.assignment.tinyledger.model.TransactionType;
import com.assignment.tinyledger.repository.AccountRepository;
import com.assignment.tinyledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Implementation for AccountService Methods
 *
 * @author Sreelekha
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;


    @Override
    public Account createAccount(Account account) {
        return repository.createAccount(account);
    }


    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        return repository.getAccountBalance(accountNumber);
    }


    @Override
    public boolean checkIfAccountExists(String accountNumber) {
        return repository.checkIfAccountExists(accountNumber);
    }


    @Override
    public BigDecimal updateBalance(String accountNumber, TransactionType transactionType, BigDecimal amount) {
        return repository.updateBalance(accountNumber, transactionType, amount);
    }
}
