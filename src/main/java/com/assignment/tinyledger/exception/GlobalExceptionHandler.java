package com.assignment.tinyledger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AccountNotFoundException.class)
    public ResponseEntity<?> accountNotExists(AccountNotFoundException accountNotFoundException) {
        return new ResponseEntity<>(accountNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidAmountException.class)
    public ResponseEntity<?> handleInputException(InvalidAmountException invalidAmountException) {
        return new ResponseEntity<>(invalidAmountException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InSufficientFundException.class)
    public ResponseEntity<?> handleInSufficientException(InSufficientFundException inSufficientFundException) {
        return new ResponseEntity<>(inSufficientFundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleInSufficientException(Exception exception) {
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = AccountAlreadyExists.class)
    public ResponseEntity<?> handleInSufficientException(AccountAlreadyExists accountAlreadyExists) {
        return new ResponseEntity<>(accountAlreadyExists.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
