package com.yome.johnbosco_management.services;

import com.yome.johnbosco_management.dtos.requests.CustomerRequestDTO;
import com.yome.johnbosco_management.dtos.requests.UpdateCustomerRequestDTO;
import com.yome.johnbosco_management.dtos.responses.CustomerResponseDTO;
import com.yome.johnbosco_management.models.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO);
    Page<CustomerResponseDTO> getAllCustomers(int page, int size);
    CustomerResponseDTO updateCustomer(Integer id, UpdateCustomerRequestDTO updateDTO);
    void deleteCustomer(Integer id);
    CustomerResponseDTO getCustomer(Integer id);

}

