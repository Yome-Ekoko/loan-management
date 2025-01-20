package com.yome.johnbosco_management.models;

import com.yome.johnbosco_management.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity{

    private String name;
    private String phone;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Loan> loans;

    @OneToOne(cascade = CascadeType.ALL)
    private SavingsAccount savingsAccount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Users user;
}
