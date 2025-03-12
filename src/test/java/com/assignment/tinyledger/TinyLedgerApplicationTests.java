package com.assignment.tinyledger;

import com.assignment.tinyledger.controller.AccountController;
import com.assignment.tinyledger.controller.LedgerController;
import com.assignment.tinyledger.exception.GlobalExceptionHandler;
import com.assignment.tinyledger.repository.AccountRepository;
import com.assignment.tinyledger.repository.AccountTransactionRepository;
import com.assignment.tinyledger.service.AccountService;
import com.assignment.tinyledger.service.LedgerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class TinyLedgerApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(applicationContext.getBean(LedgerController.class));
        Assertions.assertNotNull(applicationContext.getBean(AccountController.class));
        Assertions.assertNotNull(applicationContext.getBean(LedgerService.class));
        Assertions.assertNotNull(applicationContext.getBean(AccountService.class));
        Assertions.assertNotNull(applicationContext.getBean(GlobalExceptionHandler.class));
        Assertions.assertNotNull(applicationContext.getBean(AccountRepository.class));
        Assertions.assertNotNull(applicationContext.getBean(AccountTransactionRepository.class));
        Assertions.assertNotNull(applicationContext.getBean(GlobalExceptionHandler.class));
        Assertions.assertNotNull(applicationContext.getBean(AccountRepository.class));
    }

}
