package com.yome.johnbosco_management.dtos.requests;

import lombok.Data;

@Data
public class UpdateCustomerRequestDTO {
    private String name;
    private String email;
    private String phone;
}
