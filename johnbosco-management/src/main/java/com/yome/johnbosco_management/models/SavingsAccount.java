package com.yome.johnbosco_management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yome.johnbosco_management.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccount extends BaseEntity {

    private String accountNumber;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    @JsonBackReference
    private Customer customer;
}
