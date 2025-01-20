package com.yome.johnbosco_management.models;

import com.yome.johnbosco_management.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Role(RoleType role) {
        this.role = role;
    }

    public Role(Integer id, RoleType role) {
        this.role = role;
        this.id = id;
    }

    private RoleType role;
}
