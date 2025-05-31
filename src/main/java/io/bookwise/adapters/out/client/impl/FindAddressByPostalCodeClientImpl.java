package io.bookwise.adapters.out.client.impl;

import feign.FeignException;
import io.bookwise.adapters.out.client.FindAddressByPostalCodeClient;
import io.bookwise.adapters.out.client.dto.AddressResponse;
import io.bookwise.framework.errors.GenericErrorsEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindAddressByPostalCodeClientImpl implements FindAddressByPostalCodeClient {

    private final FindAddressByPostalCodeClient findAddressByPostalCodeClient;

    @Override
    public AddressResponse find(String postalCode) {
        return handleExceptions(
            () -> {
                var addressResponse = findAddressByPostalCodeClient.find(postalCode);
                log.info("Address found by postal code: {}", addressResponse);
                return addressResponse;
            },
            ex -> {
                if (ex instanceof FeignException && GenericErrorsEnum.BAD_REQUEST.getCode().equals(String.valueOf(((FeignException) ex).status()))) {
                    handleMessageException("Postal code not found", ex);
                    throw new RuntimeException("Postal code not found");
                }
                if (ex instanceof FeignException) {
                    handleMessageException("Error finding address by postal code", ex);
                    throw new RuntimeException("Error finding address by postal code: " + ex.getMessage());
                }
                handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
                throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
            }
        );
    }

    private <T> T handleExceptions(Supplier<T> supplier, Consumer<Exception> exceptionHandler) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            exceptionHandler.accept(ex);
            throw ex;
        }
    }

    private void handleMessageException(String msg, Exception ex) {
        log.error("{}: {}", msg, ex.getMessage());
    }

}