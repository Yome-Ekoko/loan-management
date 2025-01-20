package com.yome.johnbosco_management.dtos.requests;

import lombok.Data;

@Data
public class SavingsAccountRequestDTO {
    private Long accountId;
    private Long customerId;
    private double balance;
}
