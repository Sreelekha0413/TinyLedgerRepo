package com.assignment.tinyledger.model;

import java.math.BigDecimal;

public record TransactionResponse(String transactionId, BigDecimal availableBalance) {
}
