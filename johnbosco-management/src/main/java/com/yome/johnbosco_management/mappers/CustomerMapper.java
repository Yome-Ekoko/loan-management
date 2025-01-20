package com.yome.johnbosco_management.mappers;

import com.yome.johnbosco_management.dtos.requests.CustomerRequestDTO;
import com.yome.johnbosco_management.dtos.requests.UpdateCustomerRequestDTO;
import com.yome.johnbosco_management.dtos.responses.CustomerResponseDTO;
import com.yome.johnbosco_management.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        return new CustomerResponseDTO(customer.getId(), customer.getName(), customer.getPhone(), customer.getUser());
    }

    public Customer toEntity(CustomerRequestDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        return customer;
    }

    public void updateEntityFromDTO(UpdateCustomerRequestDTO updateDTO, Customer customer) {
        if (updateDTO.getName() != null && !updateDTO.getName().isEmpty()) {
            customer.setName(updateDTO.getName());
        }

        if (updateDTO.getPhone() != null && !updateDTO.getPhone().isEmpty()) {
            customer.setPhone(updateDTO.getPhone());
        }
    }
}
