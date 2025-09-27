package com.example.shared.mapper;

import com.example.adapter.out.client.dto.AddressResponse;
import com.example.application.core.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "AddressMapperAdapterInImpl")
public interface AddressMapper {

    AddressResponse toResponse(Address address);

}