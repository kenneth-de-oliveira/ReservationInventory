package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.Address;

public interface FindAddressByPostalCodePortOut {
    Address find(String postalCode);
}