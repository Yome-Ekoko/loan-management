package com.yome.johnbosco_management.dtos.requests;

import com.yome.johnbosco_management.enums.LoanStatus;
import lombok.Data;

@Data
public class LoanRequestDTO {
    private Long loanId;
    private double amount;
    private LoanStatus status;
    private Long customerId;
}
