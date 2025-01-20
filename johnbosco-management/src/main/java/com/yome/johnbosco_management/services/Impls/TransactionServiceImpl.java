package com.yome.johnbosco_management.services.Impls;

import com.yome.johnbosco_management.dtos.requests.CustomerRequestDTO;
import com.yome.johnbosco_management.dtos.requests.LoanRequestDTO;
import com.yome.johnbosco_management.dtos.requests.SavingsAccountRequestDTO;
import com.yome.johnbosco_management.dtos.responses.TransactionResponseDTO;
import com.yome.johnbosco_management.mappers.TransactionMapper;
import com.yome.johnbosco_management.models.Customer;
import com.yome.johnbosco_management.models.Loan;
import com.yome.johnbosco_management.models.SavingsAccount;
import com.yome.johnbosco_management.models.TransactionLog;
import com.yome.johnbosco_management.repositories.TransactionRepository;
import com.yome.johnbosco_management.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionLogRepository;

    public List<TransactionResponseDTO> getTransactionsByCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customer = convertToCustomer(customerRequestDTO);
        List<TransactionLog> transactionLogs = transactionLogRepository.findByCustomer(customer);
        return transactionLogs.stream()
                .map(TransactionMapper::ToTransactionResponseDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Page<TransactionResponseDTO> getTransactionsByAccountNumberAndDateRange(
            String accountNumber, Date startDate, Date endDate, Pageable pageable) {

        Page<TransactionLog> transactionLogsPage = transactionLogRepository
                .findBySavingsAccount_AccountNumberAndCreatedAtBetween(accountNumber, startDate, endDate, pageable);

        return transactionLogsPage.map(TransactionMapper::ToTransactionResponseDTO);
    }

    public List<TransactionResponseDTO> getTransactionsByLoan(LoanRequestDTO loanRequestDTO) {
        Loan loan = convertToLoan(loanRequestDTO);
        List<TransactionLog> transactionLogs = transactionLogRepository.findByLoan(loan);
        return transactionLogs.stream()
                .map(TransactionMapper::ToTransactionResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> getTransactionsBySavingsAccount(SavingsAccountRequestDTO savingsAccountRequestDTO) {
        SavingsAccount savingsAccount = convertToSavingsAccount(savingsAccountRequestDTO);
        List<TransactionLog> transactionLogs = transactionLogRepository.findBySavingsAccount(savingsAccount);
        return transactionLogs.stream()
                .map(TransactionMapper::ToTransactionResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> getTransactionsByDateRange(Date startDate, Date endDate) {
        List<TransactionLog> transactionLogs = transactionLogRepository.findByCreatedAtBetween(startDate, endDate);
        return transactionLogs.stream()
                .map(TransactionMapper::ToTransactionResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionResponseDTO> getPaginatedTransactionsByCustomer(CustomerRequestDTO customerRequestDTO, int page, int size) {
        Customer customer = convertToCustomer(customerRequestDTO);

        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionLog> transactionLogPage = transactionLogRepository.findPagedByCustomer(customer, pageable);

        return transactionLogPage.getContent().stream()
                .map(TransactionMapper::ToTransactionResponseDTO)
                .collect(Collectors.toList());
    }
@Override
@Transactional
    public void deleteTransactionLog(Long transactionId) {
        transactionLogRepository.deleteById(transactionId);
    }

    private Customer convertToCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customer = new Customer();
        customer.setId(customerRequestDTO.getId());
        customer.setName(customerRequestDTO.getName());
        customer.setPhone(customerRequestDTO.getPhone());
        return customer;
    }

    private Loan convertToLoan(LoanRequestDTO loanRequestDTO) {
        Loan loan = new Loan();
        loan.setAmount(loanRequestDTO.getAmount());
        loan.setStatus(loanRequestDTO.getStatus());
        return loan;
    }

    private SavingsAccount convertToSavingsAccount(SavingsAccountRequestDTO savingsAccountRequestDTO) {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setBalance(savingsAccountRequestDTO.getBalance());
        return savingsAccount;
    }
}