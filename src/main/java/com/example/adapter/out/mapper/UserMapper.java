package com.example.adapter.out.mapper;

import com.example.adapter.out.client.dto.UserRequest;
import com.example.adapter.out.client.dto.UserResponse;
import com.example.application.core.domain.Address;
import com.example.application.core.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper {

    @Mapping(source = "userAddressResponse", target = "address")
    User toDomain(UserResponse userResponse);

    @Mapping(source = "address.postalCode", target = "postalCode")
    UserRequest toRequest(User user);

}
