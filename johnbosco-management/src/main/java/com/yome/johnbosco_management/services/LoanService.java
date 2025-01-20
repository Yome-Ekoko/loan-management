package com.yome.johnbosco_management.services;

import com.yome.johnbosco_management.dtos.requests.LoanRequestDTO;
import com.yome.johnbosco_management.dtos.responses.LoanResponseDTO;
import com.yome.johnbosco_management.models.Loan;

import java.util.List;

public interface LoanService {
    LoanResponseDTO requestLoan(Integer customerId, double acctNumber);
    LoanResponseDTO rejectLoan(Long loanId, String rejectionReason);
    List<LoanResponseDTO> getLoansByCustomer(Integer customerId);
    LoanResponseDTO approveLoan(Long loanId);
}
