package com.yome.johnbosco_management.repositories;

import com.yome.johnbosco_management.models.Customer;
import com.yome.johnbosco_management.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByUser(Users user);


}
