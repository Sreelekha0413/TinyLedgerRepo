package com.assignment.tinyledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Account Doesn't Exists")
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Account Doesn't Exists");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
