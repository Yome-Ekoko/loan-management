package com.yome.johnbosco_management.services.Impls;

import com.yome.johnbosco_management.dtos.responses.SavingsResponseDTO;
import com.yome.johnbosco_management.enums.AccountType;
import com.yome.johnbosco_management.enums.TransactionType;
import com.yome.johnbosco_management.exceptions.AccountNotFoundException;
import com.yome.johnbosco_management.exceptions.CustomerAlreadyHasAccountException;
import com.yome.johnbosco_management.mappers.SavingsMapper;
import com.yome.johnbosco_management.models.Customer;
import com.yome.johnbosco_management.models.SavingsAccount;
import com.yome.johnbosco_management.models.TransactionLog;
import com.yome.johnbosco_management.repositories.CustomerRepository;
import com.yome.johnbosco_management.repositories.SavingsAccountRepository;
import com.yome.johnbosco_management.repositories.TransactionRepository;
import com.yome.johnbosco_management.services.SavingsAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavingsAccountServiceImpl implements SavingsAccountService {

    private final SavingsAccountRepository savingsAccountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionLogRepository;
    private final SavingsMapper savingsMapper;


    @Transactional
    public SavingsResponseDTO createAccount(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (savingsAccountRepository.existsByCustomer(customer)) {
            throw new CustomerAlreadyHasAccountException("Customer already has an account");
        }

        SavingsAccount account = new SavingsAccount();
        account.setCustomer(customer);
        account.setBalance(0.0);
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setAccountType(AccountType.SAVINGS);

        savingsAccountRepository.save(account);

        customer.setSavingsAccount(account);
        customerRepository.save(customer);

        return savingsMapper.toResponseDTO(account);
    }


    private String generateUniqueAccountNumber() {
        String accountNumber;
        boolean isUnique;

        do {
            accountNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            isUnique = !savingsAccountRepository.existsByAccountNumber(accountNumber);
        } while (!isUnique);

        return accountNumber;
    }

    @Transactional
    public SavingsResponseDTO creditAccount(String accountNumber, Double amount) {
        SavingsAccount account = savingsAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account with number " + accountNumber + " not found"));

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        TransactionLog log = new TransactionLog();
        log.setCustomer(account.getCustomer());
        log.setTransactionType(TransactionType.CREDIT);
        log.setAmount(amount);
        log.setBalanceAfterTransaction(newBalance);
        log.setDetails("Account credited");
        log.setCreatedAt(new Date());
        log.setSavingsAccount(account);
        log.setAccountNumber(accountNumber);
        transactionLogRepository.save(log);

        savingsAccountRepository.save(account);

        return savingsMapper.toResponseDTO(account);
    }

}