package com.example.shared.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String document;
    private String name;
    private String email;
    private String postalCode;
}