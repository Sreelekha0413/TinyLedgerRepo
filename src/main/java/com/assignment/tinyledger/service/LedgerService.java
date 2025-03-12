package com.assignment.tinyledger.service;

import com.assignment.tinyledger.exception.AccountNotFoundException;
import com.assignment.tinyledger.exception.InSufficientFundException;
import com.assignment.tinyledger.exception.InvalidAmountException;
import com.assignment.tinyledger.model.Transaction;
import com.assignment.tinyledger.model.TransactionRequest;
import com.assignment.tinyledger.model.TransactionResponse;

import java.util.List;

/**
 * Interface for Ledger account transactions
 *
 * @author Sreelekha
 */
public interface LedgerService {
    /**
     * Record the Transactions
     *
     * @param accountNumber      uniqueIdentifier of a customer
     * @param transactionRequest including details transactionType(DEPOSIT OR WITHDRAWAL),amount,transaction details
     * @return TransactionResponse which includes transactionId which uniqueIdenfier for each transaction and availableBalance after transaction is complated
     * @throws AccountNotFoundException  - if If the given accountNumber does not exist
     * @throws InvalidAmountException    - If the given amount is negative
     * @throws InSufficientFundException - If the availableBalance is less than the withdrawal amount
     */
    TransactionResponse recordTransaction(String accountNumber, TransactionRequest transactionRequest);

    /**
     * Retrieve all the transactions of accountNumber
     *
     * @param accountNumber uniqueIdentifier of a customer
     * @return list of transactions which includes transactionId, transactionType, amount, availableBalance, transactionDate
     * @throws AccountNotFoundException - if If the given accountNumber does not exist
     */
    List<Transaction> getAllTransactions(String accountNumber);
}
