package com.assignment.tinyledger.controller;

import com.assignment.tinyledger.model.Transaction;
import com.assignment.tinyledger.model.TransactionRequest;
import com.assignment.tinyledger.model.TransactionResponse;
import com.assignment.tinyledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for exposing ledger endpoints to record transactions of Deposit/Withdrawal and get the transactions of accountNumber
 *
 * @author Sreelekha
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    /**
     * Create transactions using the provided Details TransactionRequest
     *
     * @param accountNumber      uniqueIdentifier of customer
     * @param transactionRequest request with amount, trannsactionType and Details
     * @return The response entity with the TransactionResponse and HTTP status code
     */
    @PostMapping("/{accountNumber}/transactions")
    ResponseEntity<TransactionResponse> createTransaction(@PathVariable String accountNumber, @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(ledgerService.recordTransaction(accountNumber, transactionRequest), HttpStatus.CREATED);
    }

    /**
     * Create transactions using the provided Details TransactionRequest
     *
     * @param accountNumber uniqueIdentifier of customer
     * @return The response entity with the list of all the transaction details and HTTP status code
     */

    @GetMapping("/{accountNumber}/transactions")
    ResponseEntity<List<Transaction>> getAllTransactionsByAccountNumber(@PathVariable String accountNumber) {
        return new ResponseEntity<>(ledgerService.getAllTransactions(accountNumber), HttpStatus.OK);
    }

}
