package com.yome.johnbosco_management.repositories;

import com.yome.johnbosco_management.models.Customer;
import com.yome.johnbosco_management.models.Loan;
import com.yome.johnbosco_management.models.SavingsAccount;
import com.yome.johnbosco_management.models.TransactionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionLog, Long> {
    List<TransactionLog> findByCustomer(Customer customer);

    List<TransactionLog> findByLoan(Loan loan);
    Page<TransactionLog> findPagedByCustomer(Customer customer, Pageable pageable);

    Page<TransactionLog> findBySavingsAccount_AccountNumberAndCreatedAtBetween(
            String accountNumber, Date startDate, Date endDate, Pageable pageable);
    List<TransactionLog> findBySavingsAccount(SavingsAccount savingsAccount);

    List<TransactionLog> findByCreatedAtBetween(Date startDate, Date endDate);
}
