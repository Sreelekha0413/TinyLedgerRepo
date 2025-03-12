package com.assignment.tinyledger.repository.impl;

import com.assignment.tinyledger.exception.AccountAlreadyExists;
import com.assignment.tinyledger.exception.AccountNotFoundException;
import com.assignment.tinyledger.model.Account;
import com.assignment.tinyledger.model.TransactionType;
import com.assignment.tinyledger.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation for AccountRepository
 *
 * @author Sreelekha
 */

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    //Data structure of storing account details of a user account with key as accountNumber and value as account Details
    private static ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();


    @Override
    public Account createAccount(Account account) {

        if (accounts.containsKey(account.getAccountNumber()))
            throw new AccountAlreadyExists();
        Account createdAccount = Account.builder().accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .availableBalance(BigDecimal.ZERO).build();
        accounts.put(account.getAccountNumber(), createdAccount);
        return createdAccount;
    }


    @Override
    public boolean checkIfAccountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }


    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        Optional<Account> account = Optional.ofNullable(accounts.get(accountNumber));
        if (!account.isPresent())
            throw new AccountNotFoundException();
        return account.get().getAvailableBalance();
    }


    @Override
    public BigDecimal updateBalance(String accountNumber, TransactionType transactionType, BigDecimal amount) {
        Optional<Account> account = Optional.ofNullable(accounts.get(accountNumber));
        if (!account.isPresent())
            throw new AccountNotFoundException();
        BigDecimal availableBalance;
        synchronized (account) {
            availableBalance = (transactionType.equals(TransactionType.DEPOSIT) ? account.get().getAvailableBalance().add(amount) : account.get().getAvailableBalance().subtract(amount));
            account.get().setAvailableBalance(availableBalance);
            accounts.replace(accountNumber, account.get());
        }
        return availableBalance;
    }
}
