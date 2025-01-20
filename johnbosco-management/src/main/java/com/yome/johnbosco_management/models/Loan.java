package com.yome.johnbosco_management.models;

import com.yome.johnbosco_management.enums.LoanRepaymentStatus;
import com.yome.johnbosco_management.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan extends BaseEntity{

    private double amount;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    @Enumerated(EnumType.STRING)
    private LoanRepaymentStatus repaymentStatus;
    @Column(name= "is_repaid")
    private boolean isRepaid;
    @Column(name= "rejection_reason")
    private String rejectionReason;




    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
