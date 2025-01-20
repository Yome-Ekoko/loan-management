package com.yome.johnbosco_management.mappers;

import com.yome.johnbosco_management.dtos.responses.SavingsResponseDTO;
import com.yome.johnbosco_management.models.SavingsAccount;
import org.springframework.stereotype.Component;

@Component
public class SavingsMapper {

    public  SavingsResponseDTO toResponseDTO(SavingsAccount account) {
        SavingsResponseDTO responseDTO = new SavingsResponseDTO();
        responseDTO.setAccountNumber(account.getAccountNumber());
        responseDTO.setAccountType(account.getAccountType());
        responseDTO.setBalance(account.getBalance());
        responseDTO.setCustomerId(account.getCustomer().getId());
        return responseDTO;
    }
}