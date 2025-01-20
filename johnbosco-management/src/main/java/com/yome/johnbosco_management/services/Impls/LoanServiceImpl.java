package com.yome.johnbosco_management.services.Impls;

import com.yome.johnbosco_management.dtos.responses.LoanResponseDTO;
import com.yome.johnbosco_management.enums.LoanRepaymentStatus;
import com.yome.johnbosco_management.enums.LoanStatus;
import com.yome.johnbosco_management.enums.TransactionType;
import com.yome.johnbosco_management.exceptions.CustomerNotFoundException;
import com.yome.johnbosco_management.exceptions.InvalidLoanStateException;
import com.yome.johnbosco_management.exceptions.PermissionDeniedException;
import com.yome.johnbosco_management.exceptions.UnpaidLoanException;
import com.yome.johnbosco_management.mappers.LoanMapper;
import com.yome.johnbosco_management.models.*;
import com.yome.johnbosco_management.repositories.CustomerRepository;
import com.yome.johnbosco_management.repositories.LoanRepository;
import com.yome.johnbosco_management.repositories.SavingsAccountRepository;
import com.yome.johnbosco_management.repositories.TransactionRepository;
import com.yome.johnbosco_management.services.LoanService;
import com.yome.johnbosco_management.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final SavingsAccountRepository savingsAccountRepository;
    private final TransactionRepository transactionLogRepository;
    private final LoanMapper loanMapper;
    private final PermissionService permissionService;

    public LoanResponseDTO requestLoan(Integer customerId, double loanAmount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if (loanRepository.existsByCustomerAndIsRepaidFalse(customer)) {
            throw new UnpaidLoanException("Customer has an unpaid loan");
        }

        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setAmount(loanAmount);
        loan.setStatus(LoanStatus.PENDING);
        loan.setRepaymentStatus(LoanRepaymentStatus.IN_PROGRESS);

        TransactionLog log = new TransactionLog();
        log.setCustomer(customer);
        log.setTransactionType(TransactionType.LOAN_REQUEST);
        log.setAmount(loanAmount);
        log.setBalanceAfterTransaction(customer.getSavingsAccount().getBalance());
        log.setDetails("Loan requested");
        log.setCreatedAt(new Date());
        log.setSavingsAccount(customer.getSavingsAccount());
        log.setAccountNumber(customer.getSavingsAccount().getAccountNumber());

        transactionLogRepository.save(log);
        Loan savedLoan = loanRepository.save(loan);

        return loanMapper.toResponseDTO(savedLoan);
    }



    public LoanResponseDTO approveLoan(Long loanId) {
        Users loggedInUser = permissionService.checkLoggedInUser();

        // Get the logged-in user (this would typically come from the security context)
        if (!permissionService.canApproveOrRejectLoan(loggedInUser)) {
            throw new PermissionDeniedException("Access denied: Only admin can approve or reject loans");
        }

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new InvalidLoanStateException("Loan request is not in a valid state for approval.");
        }

        loan.setStatus(LoanStatus.APPROVED);
        Loan updatedLoan = loanRepository.save(loan);
        return loanMapper.toResponseDTO(updatedLoan);
    }

    public LoanResponseDTO rejectLoan(Long loanId, String rejectionReason) {
        Users loggedInUser = permissionService.checkLoggedInUser();

        if (!permissionService.canApproveOrRejectLoan(loggedInUser)) {
            throw new PermissionDeniedException("Access denied: Only admin can approve or reject loans");
        }

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new InvalidLoanStateException("Loan request is not in a valid state for rejection.");
        }

        loan.setStatus(LoanStatus.REJECTED);
        loan.setRejectionReason(rejectionReason);

        Loan updatedLoan = loanRepository.save(loan);
        return loanMapper.toResponseDTO(updatedLoan);
    }


    @Override
    public List<LoanResponseDTO> getLoansByCustomer(Integer customerId) {
        List<Loan> loans = loanRepository.findByCustomerId(customerId);
        return loans.stream()
                .map(loanMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void processLoanRepayment(Long loanId, double repaymentAmount) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        SavingsAccount account = savingsAccountRepository.findByCustomerId(loan.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        double newBalance = account.getBalance() - repaymentAmount;
        account.setBalance(newBalance);

        TransactionLog log = new TransactionLog();
        log.setCustomer(loan.getCustomer());
        log.setTransactionType(TransactionType.LOAN_REPAYMENT);
        log.setAmount(repaymentAmount);
        log.setBalanceAfterTransaction(newBalance);
        log.setDetails("Loan repayment");
        log.setCreatedAt(new Date());
        log.setSavingsAccount(account);
        log.setAccountNumber(account.getAccountNumber());
        transactionLogRepository.save(log);

        loan.setAmount(loan.getAmount() - repaymentAmount);
        if (loan.getAmount() <= 0) {
            loan.setRepaid(true);
            loan.setRepaymentStatus(LoanRepaymentStatus.COMPLETED);
        }
        loanRepository.save(loan);
        savingsAccountRepository.save(account);
    }

    @Scheduled(cron = "0 0 0 3 * ?")
    public void processLoanRepayments() {
        List<Loan> loans = loanRepository.findByStatusAndIsRepaidFalse(LoanStatus.APPROVED);
        System.out.println("Task is running every 10 seconds!");

        for (Loan loan : loans) {
            if (loan.getStatus() == LoanStatus.APPROVED && !loan.isRepaid()) {
                double repaymentAmount = loan.getAmount() * 0.10;

                SavingsAccount account = savingsAccountRepository.findByCustomerId(loan.getCustomer().getId())
                        .orElseThrow(() -> new RuntimeException("Account not found"));

                if (account.getBalance() < 0) {
                    throw new RuntimeException("Insufficient funds in the customer's account");
                }

                double newBalance = account.getBalance() - repaymentAmount;
                account.setBalance(newBalance);
                savingsAccountRepository.save(account);

                loan.setAmount(loan.getAmount() - repaymentAmount);
                if (loan.getAmount() <= 0) {
                    loan.setRepaid(true);
                    loan.setStatus(LoanStatus.COMPLETED);
                    loan.setRepaymentStatus(LoanRepaymentStatus.COMPLETED);
                }
                TransactionLog transactionLog = new TransactionLog();
                transactionLog.setLoan(loan);
                transactionLog.setCustomer(loan.getCustomer());
                transactionLog.setAmount(repaymentAmount);
                transactionLog.setTransactionType(TransactionType.LOAN_REPAYMENT);
                transactionLog.setBalanceAfterTransaction(account.getBalance());
                transactionLog.setDetails("Loan repayment made on " + LocalDateTime.now());
                transactionLog.setCreatedAt(new Date());
                transactionLog.setAccountNumber(account.getAccountNumber());

                transactionLogRepository.save(transactionLog);
            }
        }
    }
}

  /* public Loan approveLoanByCustomer(Long customerId) {
       Loan loan = loanRepository.findFirstByCustomerIdAndStatus(customerId, LoanStatus.PENDING)
               .orElseThrow(() -> new RuntimeException("No pending loan found for the customer"));

       loan.setStatus(LoanStatus.APPROVED);
       return loanRepository.save(loan);
   }

    public Loan rejectLoanByCustomer(Long customerId, String rejectionReason) {
        // Fetch the first pending loan for the customer (if any)
        Loan loan = loanRepository.findFirstByCustomerIdAndStatus(customerId, LoanStatus.PENDING)
                .orElseThrow(() -> new RuntimeException("No pending loan found for the customer"));

        loan.setStatus(LoanStatus.REJECTED);
        loan.setRejectionReason(rejectionReason);
        return loanRepository.save(loan);
    }

   */





