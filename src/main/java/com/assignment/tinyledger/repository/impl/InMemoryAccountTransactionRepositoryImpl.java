package com.assignment.tinyledger.repository.impl;

import com.assignment.tinyledger.model.Transaction;
import com.assignment.tinyledger.repository.AccountTransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation for AccountTransactionRepository
 *
 * @author Sreelekha
 */
@Repository
public class InMemoryAccountTransactionRepositoryImpl implements AccountTransactionRepository {
    //Data structure to store the transaction details of accountNumber with key as accountNumber and value as list of transaction details
    private static final ConcurrentHashMap<String, List<Transaction>> transactions = new ConcurrentHashMap<>();

    @Override
    public void createTransaction(String accountNumber, Transaction transaction) {
        if (!transactions.containsKey(accountNumber)) {
            List<Transaction> transactionList = new ArrayList<>();
            transactionList.add(transaction);
            transactions.put(accountNumber, transactionList);
        } else {
            transactions.get(accountNumber).add(transaction);
        }
    }


    @Override
    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber) {
        return Collections.unmodifiableList(transactions.get(accountNumber));
    }
}
