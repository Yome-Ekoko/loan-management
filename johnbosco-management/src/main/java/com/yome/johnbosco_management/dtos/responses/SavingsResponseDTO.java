package com.yome.johnbosco_management.dtos.responses;

import com.yome.johnbosco_management.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SavingsResponseDTO {
    private String accountNumber;
    private AccountType accountType;
    private Double balance;
    private Integer customerId;
}
