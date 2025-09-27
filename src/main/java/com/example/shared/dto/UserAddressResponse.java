package com.example.shared.dto;

import lombok.Data;

@Data
public class UserAddressResponse {
    private String street;
    private String city;
    private String state;
    private String postalCode;
}