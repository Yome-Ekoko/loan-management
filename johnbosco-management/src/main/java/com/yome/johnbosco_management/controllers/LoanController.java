package com.yome.johnbosco_management.controllers;

import com.yome.johnbosco_management.dtos.requests.LoanRequestDTO;
import com.yome.johnbosco_management.dtos.responses.LoanResponseDTO;
import com.yome.johnbosco_management.models.Loan;
import com.yome.johnbosco_management.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/{customerId}/request")
    public ResponseEntity<LoanResponseDTO> requestLoan(@PathVariable Integer customerId,
                                                       @RequestParam double loanAmount) {
        try {
            LoanResponseDTO loanResponseDTO = loanService.requestLoan(customerId, loanAmount);
            return new ResponseEntity<>(loanResponseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{loanId}/approve")
    public ResponseEntity<LoanResponseDTO> approveLoan(@PathVariable Long loanId) {
        return new ResponseEntity<>(loanService.approveLoan(loanId), HttpStatus.OK) ;
    }

    @PutMapping("/{loanId}/reject")
    public ResponseEntity<LoanResponseDTO> rejectLoan(@PathVariable Long loanId, @RequestParam String rejectionReason) {
        return new ResponseEntity<>(loanService.rejectLoan(loanId, rejectionReason), HttpStatus.OK);
    }


    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByCustomer(@PathVariable Integer customerId) {
        return new ResponseEntity<> (loanService.getLoansByCustomer(customerId), HttpStatus.OK);
    }
}