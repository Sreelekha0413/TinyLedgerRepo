package com.assignment.tinyledger.service;

import com.assignment.tinyledger.model.Account;
import com.assignment.tinyledger.model.TransactionType;

import java.math.BigDecimal;

/**
 * Interface for account creation service
 *
 * @author Sreelekha
 */
public interface AccountService {

    /**
     * Create Account using the provided Details Account
     *
     * @param account account details
     * @return created account details
     */
    Account createAccount(Account account);

    /**
     * Retrieve Account Balance using the accountNumber
     *
     * @param accountNumber uniqueIdentifier of a customer
     * @return availablebalance in BigDecimal format
     */
    BigDecimal getAccountBalance(String accountNumber);

    /**
     * Checks if Account is exists or not
     *
     * @param accountNumber uniqueIdentifier of a customer
     * @return true if given accountNumber exists otherwise false will be returned
     */
    boolean checkIfAccountExists(String accountNumber);

    /**
     * update accountBalance based on transactionType
     *
     * @param accountNumber   uniqueIdentifier of a customer
     * @param transactionType transactionType as DEPOSIT OR WITHDRAWAL
     * @param amount          transaction amount
     * @return availableBalance after applying operation addition if the transactionType is Deposit else Subtract
     * @throws AccountNotFoundException - if If the given accountNumber does not exist
     */
    BigDecimal updateBalance(String accountNumber, TransactionType transactionType, BigDecimal amount);
}
