package com.example.adapter.out.mapper;

import com.example.adapter.out.client.dto.UserAddressResponse;
import com.example.application.core.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "street", target = "street")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "postalCode", target = "postalCode")
    Address toDomain(UserAddressResponse userAddressResponse);

}