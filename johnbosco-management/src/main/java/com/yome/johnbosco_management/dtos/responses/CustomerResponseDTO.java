package com.yome.johnbosco_management.dtos.responses;

import com.yome.johnbosco_management.models.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {
    private Integer id;
    private String name;
    private String phone;
    private Users user;
}
