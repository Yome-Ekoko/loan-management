package com.yome.johnbosco_management.repositories;

import com.yome.johnbosco_management.models.Customer;
import com.yome.johnbosco_management.models.Loan;
import com.yome.johnbosco_management.models.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
    boolean existsByCustomer(Customer customer);
    Optional<SavingsAccount> findByCustomerId(Integer customerId); // Find SavingsAccount by Customer's ID
    boolean existsByAccountNumber(String accountNumber); // Check if account number exists
    Optional<SavingsAccount> findByAccountNumber(String accountNumber);

}
