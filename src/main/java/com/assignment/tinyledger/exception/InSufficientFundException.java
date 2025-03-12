package com.assignment.tinyledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Insufficient Fund")
public class InSufficientFundException extends RuntimeException {

    public InSufficientFundException() {
        super("Insufficient Fund");
    }

    public InSufficientFundException(String message) {
        super(message);
    }
}
