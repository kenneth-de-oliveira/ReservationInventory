package com.example.application.core.port.in;

import com.example.application.core.domain.Address;

public interface FindAddressByPostalCodePortIn {
    Address find(String postalCode);
}