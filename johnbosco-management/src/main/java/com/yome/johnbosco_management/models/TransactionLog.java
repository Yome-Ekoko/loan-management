package com.yome.johnbosco_management.models;

import com.yome.johnbosco_management.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLog extends BaseEntity{



    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private double amount;
    private double balanceAfterTransaction;
    private String details;
    private String accountNumber;

    @ManyToOne
    private SavingsAccount savingsAccount;

    @ManyToOne
    private Loan loan;
}
