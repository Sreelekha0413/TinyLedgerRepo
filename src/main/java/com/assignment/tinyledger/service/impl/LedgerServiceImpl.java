package com.assignment.tinyledger.service.impl;

import com.assignment.tinyledger.exception.AccountNotFoundException;
import com.assignment.tinyledger.exception.InSufficientFundException;
import com.assignment.tinyledger.exception.InvalidAmountException;
import com.assignment.tinyledger.model.Transaction;
import com.assignment.tinyledger.model.TransactionRequest;
import com.assignment.tinyledger.model.TransactionResponse;
import com.assignment.tinyledger.model.TransactionType;
import com.assignment.tinyledger.repository.AccountTransactionRepository;
import com.assignment.tinyledger.service.AccountService;
import com.assignment.tinyledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation for LedgerService
 *
 * @author Sreelekha
 */
@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService {

    private final AccountTransactionRepository repository;

    private final AccountService accountService;

    @Override
    public TransactionResponse recordTransaction(String accountNumber, TransactionRequest transactionRequest) {
        if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidAmountException();
        boolean isAccountExists = accountService.checkIfAccountExists(accountNumber);
        if (!isAccountExists)
            throw new AccountNotFoundException();
        //Check if the sufficient balance is available in the account before withdrawal
        if (transactionRequest.getTransactionType().equals(TransactionType.WITHDRAWAL)) {

            if (isSufficientBalanceExists(accountNumber, transactionRequest))
                throw new InSufficientFundException();
        }
        //update the account balance for each transaction
        BigDecimal availableBalance = accountService.updateBalance(accountNumber, transactionRequest.getTransactionType(), transactionRequest.getAmount());
        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .amount(transactionRequest.getAmount())
                .transactionType(transactionRequest.getTransactionType())
                .availableBalance(availableBalance)
                .transactionDate(LocalDateTime.now()).build();
        repository.createTransaction(accountNumber, transaction);
        return new TransactionResponse(transactionId, availableBalance);
    }


    @Override
    public List<Transaction> getAllTransactions(String accountNumber) {
        boolean isAccountExists = accountService.checkIfAccountExists(accountNumber);
        if (!isAccountExists)
            throw new AccountNotFoundException();
        return repository.getAllTransactionsByAccountNumber(accountNumber);
    }

    /**
     * Checks if the sufficient balance is available
     *
     * @param accountNumber uniqueIdentifier of a customer
     * @param request       - includes amount and transactionType
     * @return true if the available balance is greater than the given amount else false.
     */
    private boolean isSufficientBalanceExists(String accountNumber, TransactionRequest request) {
        return accountService.getAccountBalance(accountNumber).subtract(request.getAmount()).compareTo(BigDecimal.ZERO) < 0;
    }
}
