package com.example.shared.mapper;

import com.example.adapter.out.client.dto.UserRequest;
import com.example.adapter.out.client.dto.UserResponse;
import com.example.application.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class, implementationName = "UserMapperAdapterInImpl")
public interface UserMapper {

    @Mapping(source = "address", target = "userAddressResponse")
    UserResponse toResponse(User user);

    @Mapping(source = "postalCode", target = "address.postalCode")
    User toDomain(UserRequest userRequest);

}
