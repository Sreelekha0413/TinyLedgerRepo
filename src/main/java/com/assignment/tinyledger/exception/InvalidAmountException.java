package com.assignment.tinyledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Amount. Amount should be greater than 0")
public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException() {
        super("Invalid Amount. Amount should be greater than 0");
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
