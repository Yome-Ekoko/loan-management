package com.yome.johnbosco_management.service.impl;

import com.yome.johnbosco_management.dtos.responses.SavingsResponseDTO;
import com.yome.johnbosco_management.enums.AccountType;
import com.yome.johnbosco_management.enums.RoleType;
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
import com.yome.johnbosco_management.services.Impls.SavingsAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SavingsAccountServiceTest {

    private SavingsAccountRepository savingsAccountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private SavingsMapper savingsMapper;
    private SavingsAccountServiceImpl savingsAccountService;

    @BeforeEach
    void setUp() {
        savingsAccountRepository = Mockito.mock(SavingsAccountRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        savingsMapper = Mockito.mock(SavingsMapper.class);

        savingsAccountService = new SavingsAccountServiceImpl(savingsAccountRepository, customerRepository, transactionRepository, savingsMapper);
    }

    @Test
    void createAccount_HappyPath() {
        Integer customerId = 2;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Jane");

        SavingsAccount account = new SavingsAccount();
        account.setAccountNumber("93649369472");
        account.setBalance(0.0);
        account.setCustomer(customer);
        account.setAccountType(AccountType.SAVINGS);
        account.setCustomer(customer);

        SavingsResponseDTO responseDTO = new SavingsResponseDTO();
        responseDTO.setAccountNumber(account.getAccountNumber());
        responseDTO.setBalance(account.getBalance());
        responseDTO.setCustomerId(account.getCustomer().getId());
        responseDTO.setAccountType(account.getAccountType());


        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        when(savingsAccountRepository.existsByCustomer(customer)).thenReturn(false);
        when(savingsAccountRepository.save(any(SavingsAccount.class))).thenReturn(account);
        when(savingsMapper.toResponseDTO(account)).thenReturn(responseDTO);


        SavingsResponseDTO result = savingsAccountService.createAccount(customerId);
        System.out.println("Result: " + customer);

        verify(customerRepository).findById(customerId);
        verify(savingsAccountRepository).save(any(SavingsAccount.class));

        assertNotNull(result, "The result should not be null");
        assertTrue(result.getAccountNumber().length() == 10);
        assertEquals(0.0, result.getBalance());
    }


    @Test
    void createAccount_CustomerAlreadyHasAccount() {
        Integer customerId = 2;
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        when(savingsAccountRepository.existsByCustomer(customer)).thenReturn(true);

        assertThrows(CustomerAlreadyHasAccountException.class, () -> savingsAccountService.createAccount(customerId));

        verify(customerRepository).findById(customerId);
        verify(savingsAccountRepository).existsByCustomer(customer);
    }

    @Test
    void creditAccount_HappyPath() {
        String accountNumber = "96f1f6a7f8";
        Double amount = 100.0;

        SavingsAccount account = new SavingsAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(200.0);

        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setAmount(amount);
        transactionLog.setTransactionType(TransactionType.CREDIT);
        transactionLog.setBalanceAfterTransaction(account.getBalance() + amount);

        SavingsResponseDTO responseDTO = new SavingsResponseDTO();
        responseDTO.setAccountNumber(accountNumber);
        responseDTO.setBalance(account.getBalance() + amount);

        when(savingsAccountRepository.findByAccountNumber(accountNumber)).thenReturn(java.util.Optional.of(account));
        when(savingsAccountRepository.save(account)).thenReturn(account);
        when(transactionRepository.save(any(TransactionLog.class))).thenReturn(transactionLog);
        when(savingsMapper.toResponseDTO(account)).thenReturn(responseDTO);

        SavingsResponseDTO result = savingsAccountService.creditAccount(accountNumber, amount);

        verify(savingsAccountRepository).findByAccountNumber(accountNumber);
        verify(savingsAccountRepository).save(account);
        verify(transactionRepository).save(any(TransactionLog.class));
        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        assertEquals(300.0, result.getBalance());
    }

    @Test
    void creditAccount_AccountNotFound() {
        String accountNumber = "nonexistent123";
        Double amount = 100.0;

        when(savingsAccountRepository.findByAccountNumber(accountNumber)).thenReturn(java.util.Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> savingsAccountService.creditAccount(accountNumber, amount));

        verify(savingsAccountRepository).findByAccountNumber(accountNumber);
    }
}
