package com.yome.johnbosco_management.dtos.responses;

import com.yome.johnbosco_management.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class TransactionResponseDTO {
    private Integer id;
    private TransactionType transactionType;
    private double amount;
    private Date transactionDate;
    private String savingsAccountNumber;
    private Integer loanId;
}
