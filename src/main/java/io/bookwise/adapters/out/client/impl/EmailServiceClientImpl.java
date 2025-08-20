package io.bookwise.adapters.out.client.impl;

import feign.FeignException;
import io.bookwise.adapters.out.client.EmailServiceClient;
import io.bookwise.adapters.out.client.dto.EmailRequest;
import io.bookwise.framework.errors.GenericErrorsEnum;
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
    public void sendEmail(EmailRequest request) {

        handleExceptions(() -> {
            log.info("Sending mail to: {}, subject: {}, text: {}", request.getTo(), request.getSubject(), request.getText());
            emailServiceClient.sendEmail(request);
            log.info("Email sent successfully to: {}", request.getTo());
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