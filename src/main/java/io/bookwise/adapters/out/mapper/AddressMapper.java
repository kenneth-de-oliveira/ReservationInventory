package io.bookwise.adapters.out.mapper;

import io.bookwise.adapters.out.client.dto.AddressResponse;
import io.bookwise.adapters.out.repository.entity.AddressEntity;
import io.bookwise.application.core.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toDomain(AddressEntity addressEntity);

    AddressEntity toEntity(Address address);

    @Mapping(target = "id", ignore = true)
    Address toDomain(AddressResponse addressResponse);

}