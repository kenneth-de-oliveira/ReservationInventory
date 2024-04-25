package io.bookwise.adapters.out.mapper;

import io.bookwise.adapters.out.client.dto.AddressResponse;
import io.bookwise.adapters.out.repository.entity.AddressEntity;
import io.bookwise.application.core.domain.Address;

public class AddressMapper {

    public static Address toDomain(AddressEntity addressEntity) {
        var address = new Address();
        address.setId(addressEntity.getId());
        address.setCity(addressEntity.getCity());
        address.setState(addressEntity.getState());
        address.setStreet(addressEntity.getStreet());
        address.setPostalCode(addressEntity.getPostalCode());
        return address;
    }

    public static AddressEntity toEntity(Address address) {
        var addressEntity = new AddressEntity();
        addressEntity.setCity(address.getCity());
        addressEntity.setState(address.getState());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setPostalCode(address.getPostalCode());
        return addressEntity;
    }

    public static Address toDomain(AddressResponse addressResponse) {
        var address = new Address();
        address.setCity(addressResponse.city());
        address.setState(addressResponse.state());
        address.setStreet(addressResponse.street());
        address.setPostalCode(addressResponse.postalCode());
        return address;
    }

}