package com.example.shared.mapper;

import com.example.application.core.dto.Email;
import com.example.shared.dto.EmailRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailRequest toRequest(Email email);

}