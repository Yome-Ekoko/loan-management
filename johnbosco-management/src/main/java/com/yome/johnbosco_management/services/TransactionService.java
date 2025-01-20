package com.yome.johnbosco_management.services;


import com.yome.johnbosco_management.dtos.requests.CustomerRequestDTO;
import com.yome.johnbosco_management.dtos.requests.LoanRequestDTO;
import com.yome.johnbosco_management.dtos.requests.SavingsAccountRequestDTO;
import com.yome.johnbosco_management.dtos.responses.TransactionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    void deleteTransactionLog(Long transactionId);
    List<TransactionResponseDTO> getPaginatedTransactionsByCustomer(CustomerRequestDTO customerRequestDTO, int page, int size);
    List<TransactionResponseDTO> getTransactionsByDateRange(Date startDate, Date endDate);
    List<TransactionResponseDTO> getTransactionsBySavingsAccount(SavingsAccountRequestDTO savingsAccountRequestDTO);
    List<TransactionResponseDTO> getTransactionsByLoan(LoanRequestDTO loanRequestDTO);
    List<TransactionResponseDTO> getTransactionsByCustomer(CustomerRequestDTO customerRequestDTO);
    Page<TransactionResponseDTO> getTransactionsByAccountNumberAndDateRange(
            String accountNumber, Date startDate, Date endDate, Pageable pageable);
}
