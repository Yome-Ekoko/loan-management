package com.yome.johnbosco_management.repositories;

import com.yome.johnbosco_management.dtos.requests.LoanRequestDTO;
import com.yome.johnbosco_management.enums.LoanStatus;
import com.yome.johnbosco_management.models.Customer;
import com.yome.johnbosco_management.models.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface  LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findById(Integer id);

    Page<Loan> findAll(Pageable pageable);
    List<Loan> findByCustomerId(Integer customerId);
    Optional<Loan> findFirstByCustomerIdAndStatus(Long customerId, LoanStatus status);
    List<Loan> findByStatusAndIsRepaidFalse(LoanStatus status);

    boolean existsByCustomerAndIsRepaidFalse(Customer customer);

    Page<LoanRequestDTO> findByCustomerIdAndStatus(Long customerId, LoanStatus status, Pageable pageable);
}