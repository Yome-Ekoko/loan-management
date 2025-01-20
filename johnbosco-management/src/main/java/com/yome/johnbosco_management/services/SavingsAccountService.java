package com.yome.johnbosco_management.services;

import com.yome.johnbosco_management.dtos.responses.SavingsResponseDTO;
import com.yome.johnbosco_management.models.SavingsAccount;

public interface SavingsAccountService {
    SavingsResponseDTO creditAccount(String accountNumber, Double amount);
    SavingsResponseDTO createAccount(Integer customerId);
}
