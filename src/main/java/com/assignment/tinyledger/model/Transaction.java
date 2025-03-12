package com.assignment.tinyledger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    private String transactionId;

    private TransactionType transactionType;

    private BigDecimal amount;

    private BigDecimal availableBalance;

    private LocalDateTime transactionDate;
}
