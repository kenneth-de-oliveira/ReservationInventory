package com.example.application.core.port.out;

import com.example.application.core.domain.Address;

public interface FindAddressByPostalCodePortOut {
    Address find(String postalCode);
}