package com.example.adapter.out.client.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String document;
    private String name;
    private String email;
    private UserAddressResponse userAddressResponse;
}