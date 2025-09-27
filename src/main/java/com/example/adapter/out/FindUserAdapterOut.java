package com.example.adapter.out;

import com.example.adapter.out.client.UserManagementServiceClient;
import com.example.adapter.out.mapper.UserMapper;
import com.example.application.core.domain.User;
import com.example.application.core.port.out.FindUserPortOut;
import com.example.infrastructure.errors.GenericErrorsEnum;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindUserAdapterOut implements FindUserPortOut {

    private final UserManagementServiceClient userManagementServiceClient;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByDocument(String document) {
        try {
            log.info("Sending request to User Management Service to find user by document: {}", document);
            var userResponse = userManagementServiceClient.findByDocument(document);
            var optUser = Optional.ofNullable(mapper.toDomain(userResponse));
            log.info("Found user in User Management Service: {}", userResponse);
            return optUser;
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