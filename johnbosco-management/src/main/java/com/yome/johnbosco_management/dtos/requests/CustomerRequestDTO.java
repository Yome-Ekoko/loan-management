package com.yome.johnbosco_management.dtos.requests;

import lombok.Data;


@Data
public class CustomerRequestDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String Password;
}
