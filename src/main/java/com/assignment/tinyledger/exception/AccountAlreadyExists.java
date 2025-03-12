package com.assignment.tinyledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account Already Exists")
public class AccountAlreadyExists extends RuntimeException {

    public AccountAlreadyExists() {
        super("Account Already Exists");
    }

    public AccountAlreadyExists(String message) {
        super(message);
    }
}
