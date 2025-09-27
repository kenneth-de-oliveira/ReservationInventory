package com.example.shared.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String document;
    private String name;
    private String email;
    private UserAddressResponse userAddressResponse;
}