package com.yome.johnbosco_management.controllers;

import com.yome.johnbosco_management.dtos.requests.CustomerRequestDTO;
import com.yome.johnbosco_management.dtos.requests.UpdateCustomerRequestDTO;
import com.yome.johnbosco_management.dtos.responses.CustomerResponseDTO;
import com.yome.johnbosco_management.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerDTO) {
        CustomerResponseDTO customer = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable Integer id) {
        CustomerResponseDTO customer = customerService.getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> getAllCustomers(@RequestParam int page, @RequestParam int size) {
        Page<CustomerResponseDTO> customers = customerService.getAllCustomers(page, size);
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Integer id, @RequestBody UpdateCustomerRequestDTO customerDTO) {
        CustomerResponseDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
