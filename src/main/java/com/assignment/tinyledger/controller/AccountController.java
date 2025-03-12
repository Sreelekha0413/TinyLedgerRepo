package com.assignment.tinyledger.controller;

import com.assignment.tinyledger.model.Account;
import com.assignment.tinyledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Controller for exposing account endpoints to create account or get account balance
 *
 * @author Sreelekha
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    /**
     * Create an account using the provided Details of account
     *
     * @param account The account details
     * @return The response entity with the created account and HTTP status code
     */

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    /**
     * Get an account Balance using accountNumber
     *
     * @param accountNumber The account number of a user
     * @return The response entity with the available balance in BigDecimal Type and HTTP status code
     */

    @GetMapping("/{accountNumber}/balance")
    ResponseEntity<BigDecimal> accountBalance(@PathVariable String accountNumber) {
         return new ResponseEntity<>(accountService.getAccountBalance(accountNumber), HttpStatus.OK);
    }
}
