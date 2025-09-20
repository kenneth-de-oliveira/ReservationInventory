package com.example.adapter.out;

import com.example.adapter.out.client.UserManagementServiceClient;
import com.example.adapter.out.mapper.UserMapper;
import com.example.application.core.domain.User;
import com.example.application.core.port.out.CreateUserPortOut;
import com.example.infrastructure.errors.GenericErrorsEnum;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserAdapterOut implements CreateUserPortOut {

    private final UserManagementServiceClient userManagementServiceClient;
    private final UserMapper mapper;

    @Override
    public User create(User user) {
        try {
            log.info("Sending request to User Management Service to create user: {}", user);
            var userRequest = mapper.toRequest(user);
            var userResponse = userManagementServiceClient.create(userRequest);
            log.info("Created user in User Management Service: {}", userResponse);
            return mapper.toDomain(userResponse);
        } catch (FeignException ex) {
            handleMessageException("Error sending request to User Management Service: ", ex);
            throw new RuntimeException("Error sending request to User Management Service: " + ex.getMessage());
        } catch (Exception ex) {
            handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
            throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
        }
    }

    private void handleMessageException(String msg, Exception ex) {
        log.error("{}: {}", msg, ex.getMessage());
    }

}