package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.domain.Address;

public interface FindAddressByPostalCodePortIn {
    Address find(String postalCode);
}