package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.FindAddressByPostalCodeClientImpl;
import io.bookwise.adapters.out.mapper.AddressMapper;
import io.bookwise.application.core.domain.Address;
import io.bookwise.application.core.ports.out.FindAddressByPostalCodePortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindAddressByPostalCodeAdapterOut implements FindAddressByPostalCodePortOut {

    private final FindAddressByPostalCodeClientImpl findAddressByPostalCodeClientImpl;

    @Override
    public Address find(String postalCode) {
        log.info("Finding address by postal code: {}", postalCode);
        return AddressMapper.toDomain(
                findAddressByPostalCodeClientImpl.find(postalCode)
        );
    }

}