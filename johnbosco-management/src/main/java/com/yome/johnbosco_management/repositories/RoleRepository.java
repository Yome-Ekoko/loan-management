package com.yome.johnbosco_management.repositories;

import com.yome.johnbosco_management.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   // Optional<Role> findByName(String name);
}
