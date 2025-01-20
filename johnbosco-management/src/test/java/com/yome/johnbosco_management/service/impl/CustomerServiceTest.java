package com.yome.johnbosco_management.service.impl;

import com.yome.johnbosco_management.dtos.requests.CustomerRequestDTO;
import com.yome.johnbosco_management.dtos.responses.CustomerResponseDTO;
import com.yome.johnbosco_management.mappers.CustomerMapper;
import com.yome.johnbosco_management.models.Customer;
import com.yome.johnbosco_management.repositories.CustomerRepository;
import com.yome.johnbosco_management.repositories.RoleRepository;
import com.yome.johnbosco_management.repositories.UserRepository;
import com.yome.johnbosco_management.services.Impls.CustomerServiceImpl;
import com.yome.johnbosco_management.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;
    private CustomerMapper customerMapper;
    private CustomerService customerService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        customerMapper = Mockito.mock(CustomerMapper.class);
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class); // Added RoleRepository mock

        customerService = new CustomerServiceImpl(customerRepository, passwordEncoder, customerMapper, userRepository, roleRepository);
    }

    @Test
    void shouldCreateCustomerWhenDataIsValid() {
        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        customerRequest.setName("John Doe");
        customerRequest.setEmail("john.doe@example.com");
        customerRequest.setPassword("password123");

        Customer customer = new Customer();
        customer.setName("John Doe");

        CustomerResponseDTO customerResponse = new CustomerResponseDTO();
        customerResponse.setName("John Doe");
        customerResponse.setEmail("john.doe@example.com");

        when(customerRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(customerMapper.toEntity(customerRequest)).thenReturn(customer);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toResponseDTO(any(Customer.class))).thenReturn(customerResponse);

        CustomerResponseDTO result = customerService.createCustomer(customerRequest);

        verify(customerRepository).existsByEmail("john.doe@example.com");
        verify(customerRepository).save(any(Customer.class));
        verify(userRepository).save(any()); // Verifies the user was also saved

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("John Doe", result.getName()),
                () -> assertEquals("john.doe@example.com", result.getEmail())
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        customerRequest.setName("John Doe");
        customerRequest.setEmail("john.doe@example.com");

        when(customerRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerService.createCustomer(customerRequest));

        verify(customerRepository).existsByEmail("john.doe@example.com");
        assertEquals("Email is already exists", exception.getMessage());
    }

    @Test
    void shouldHandlePasswordEncoding() {
        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        customerRequest.setName("John Doe");
        customerRequest.setEmail("john.doe@example.com");
        customerRequest.setPassword("password123");

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");

        when(customerRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(customerMapper.toEntity(customerRequest)).thenReturn(customer);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        customerService.createCustomer(customerRequest);

        verify(passwordEncoder).encode("password123");
    }
}
