package com.yome.johnbosco_management.controllers;

import com.yome.johnbosco_management.dtos.requests.CustomerRequestDTO;
import com.yome.johnbosco_management.dtos.requests.LoanRequestDTO;
import com.yome.johnbosco_management.dtos.requests.SavingsAccountRequestDTO;
import com.yome.johnbosco_management.dtos.responses.TransactionResponseDTO;
import com.yome.johnbosco_management.models.TransactionLog;
import com.yome.johnbosco_management.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/customer")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        return new ResponseEntity<>(transactionService.getTransactionsByCustomer(customerRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/loan")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
        return new ResponseEntity<>(transactionService.getTransactionsByLoan(loanRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/savingsAccount")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsBySavingsAccount(@RequestBody SavingsAccountRequestDTO savingsAccountRequestDTO) {
        return new ResponseEntity<> (transactionService.getTransactionsBySavingsAccount(savingsAccountRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/paginated/customer")
    public ResponseEntity<List<TransactionResponseDTO>> getPaginatedTransactionsByCustomer(
            @RequestBody CustomerRequestDTO customerRequestDTO,
            @RequestParam int page,
            @RequestParam int size) {
        return new ResponseEntity<>(transactionService.getPaginatedTransactionsByCustomer(customerRequestDTO, page, size), HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransactionLog(transactionId);
    }
    @GetMapping("/account/{accountNumber}")
    public Page<TransactionResponseDTO> getTransactions(
            @PathVariable String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        return transactionService.getTransactionsByAccountNumberAndDateRange(accountNumber, startDate, endDate, pageable);
    }
}
