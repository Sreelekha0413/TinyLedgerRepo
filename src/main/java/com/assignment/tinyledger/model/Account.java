package com.assignment.tinyledger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    private String accountNumber;

    private BigDecimal availableBalance;

    private String accountType;
}
