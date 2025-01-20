package com.yome.johnbosco_management.services.Impls;

import com.yome.johnbosco_management.dtos.requests.CustomerRequestDTO;
import com.yome.johnbosco_management.dtos.requests.UpdateCustomerRequestDTO;
import com.yome.johnbosco_management.dtos.responses.CustomerResponseDTO;
import com.yome.johnbosco_management.enums.RoleType;
import com.yome.johnbosco_management.mappers.CustomerMapper;
import com.yome.johnbosco_management.models.Customer;
import com.yome.johnbosco_management.models.Role;
import com.yome.johnbosco_management.models.Users;
import com.yome.johnbosco_management.repositories.CustomerRepository;
import com.yome.johnbosco_management.repositories.RoleRepository;
import com.yome.johnbosco_management.repositories.UserRepository;
import com.yome.johnbosco_management.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerDTO) {
        if (userRepository.findByUsername(customerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already exists");
        }
       Role role= new Role(RoleType.CUSTOMER);
        roleRepository.save(role);
        Users user = new Users();
        user.setUsername(customerDTO.getEmail());
        user.setPassword(customerDTO.getPassword());
        user.setRole(role);

        userRepository.save(user);

        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setUser(user);

        Customer savedCustomer = customerRepository.save(customer);


        return customerMapper.toResponseDTO(savedCustomer);
    }

    public CustomerResponseDTO getCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return customerMapper.toResponseDTO(customer);
    }

    public CustomerResponseDTO updateCustomer(Integer id, UpdateCustomerRequestDTO updateDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerMapper.updateEntityFromDTO(updateDTO, customer);

        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.toResponseDTO(updatedCustomer);
    }


    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }

    public Page<CustomerResponseDTO> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerRepository.findAll(pageable);

        return customers.map(customerMapper::toResponseDTO);
    }
}
