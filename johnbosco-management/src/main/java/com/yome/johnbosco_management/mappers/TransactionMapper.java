package com.yome.johnbosco_management.mappers;

import com.yome.johnbosco_management.dtos.responses.TransactionResponseDTO;
import com.yome.johnbosco_management.models.TransactionLog;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public static TransactionResponseDTO ToTransactionResponseDTO(TransactionLog transactionLog) {
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();

        responseDTO.setId(transactionLog.getId());
        responseDTO.setTransactionType(transactionLog.getTransactionType());
        responseDTO.setAmount(transactionLog.getAmount());
        responseDTO.setTransactionDate(transactionLog.getCreatedAt());
        responseDTO.setSavingsAccountNumber(transactionLog.getSavingsAccount().getAccountNumber());

        if (transactionLog.getLoan() != null) {
            responseDTO.setLoanId(transactionLog.getLoan().getId());
        }

        return responseDTO;
    }
}

