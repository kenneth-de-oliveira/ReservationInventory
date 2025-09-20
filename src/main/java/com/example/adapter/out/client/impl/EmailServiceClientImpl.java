package com.example.adapter.out.client.impl;

import feign.FeignException;
import com.example.adapter.out.client.EmailServiceClient;
import com.example.adapter.out.client.dto.EmailDTO;
import com.example.infrastructure.errors.GenericErrorsEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailServiceClientImpl implements EmailServiceClient {

    private final EmailServiceClient emailServiceClient;

    @Override
    public void send(EmailDTO emailDTO) {

        handleExceptions(() -> {
            log.info("Sending mail to: {}, subject: {}, text: {}", emailDTO.getTo(), emailDTO.getSubject(), emailDTO.getText());
            emailServiceClient.send(emailDTO);
            log.info("Email sent successfully to: {}", emailDTO.getTo());
            return null;
        }, ex -> {
            if (ex instanceof FeignException) {
                handleMessageException("Error sending email", ex);
                throw new RuntimeException("Error sending email: " + ex.getMessage());
            }
            handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
            throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
        });

    }

    private <T> void handleExceptions(Supplier<T> supplier, Consumer<Exception> exceptionHandler) {
        try {
            supplier.get();
        } catch (Exception ex) {
            exceptionHandler.accept(ex);
            throw ex;
        }
    }

    private void handleMessageException(String msg, Exception ex) {
        log.error("{}: {}", msg, ex.getMessage());
    }

}