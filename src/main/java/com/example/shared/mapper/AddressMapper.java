package com.example.shared.mapper;

import com.example.application.core.domain.Address;
import com.example.shared.dto.AddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "AddressMapperAdapterInImpl")
public interface AddressMapper {

    AddressResponse toResponse(Address address);

}