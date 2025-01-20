package com.yome.johnbosco_management.dtos.responses;

import com.yome.johnbosco_management.enums.LoanRepaymentStatus;
import com.yome.johnbosco_management.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@RequiredArgsConstructor
public class LoanResponseDTO {
    private Integer loanId;
    private  Integer customerId;
    private double amount;
    private LoanStatus status;
    private LoanRepaymentStatus repaymentStatus;
    private  boolean isRepaid;
}
