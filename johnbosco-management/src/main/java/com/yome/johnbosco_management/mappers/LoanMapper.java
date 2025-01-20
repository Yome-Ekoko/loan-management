package com.yome.johnbosco_management.mappers;

import com.yome.johnbosco_management.dtos.requests.LoanRequestDTO;
import com.yome.johnbosco_management.dtos.responses.LoanResponseDTO;
import com.yome.johnbosco_management.models.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanResponseDTO toResponseDTO(Loan loan) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setLoanId(loan.getId());
        dto.setCustomerId(loan.getCustomer().getId());
        dto.setAmount(loan.getAmount());
        dto.setStatus(loan.getStatus());
        dto.setRepaymentStatus(loan.getRepaymentStatus());
        dto.setRepaid(loan.isRepaid());
        return dto;
    }
}