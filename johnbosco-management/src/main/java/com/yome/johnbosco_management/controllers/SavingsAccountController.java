package com.yome.johnbosco_management.controllers;

import com.yome.johnbosco_management.dtos.responses.SavingsResponseDTO;
import com.yome.johnbosco_management.models.SavingsAccount;
import com.yome.johnbosco_management.services.SavingsAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/savings")
public class SavingsAccountController {

    private final SavingsAccountService savingsAccountService;

    @PostMapping
    public ResponseEntity<SavingsResponseDTO> createSavingsAccount(@RequestParam Integer customerId) {
        return new ResponseEntity<>(savingsAccountService.createAccount(customerId), HttpStatus.OK);
    }

    @PutMapping("/{accountNumber}/credit")
    public ResponseEntity<SavingsResponseDTO> creditAccount(
            @PathVariable String accountNumber,
            @RequestParam Double amount) {

            SavingsResponseDTO updatedAccount = savingsAccountService.creditAccount(accountNumber, amount);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);

    }
}
