package com.yome.johnbosco_management.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class StatementResponseDTO {

    private String transactionType;
    private Double amount;
    private Double balanceAfterTransaction;
    private Date transactionDate;

}