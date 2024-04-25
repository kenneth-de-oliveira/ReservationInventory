package io.bookwise.adapters.out.client;

import feign.FeignException;
import io.bookwise.adapters.out.client.dto.AddressResponse;
import io.bookwise.framework.errors.GenericErrorsEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindAddressByPostalCodeClientImpl implements FindAddressByPostalCodeClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindAddressByPostalCodeClientImpl.class);

    private final FindAddressByPostalCodeClient findAddressByPostalCodeClient;

    @Override
    public AddressResponse find(String postalCode) {
        try {
            AddressResponse addressResponse = findAddressByPostalCodeClient.find(postalCode);
            log.info("Address found by postal code: {}", addressResponse);
            return addressResponse;
        } catch (FeignException ex) {
            if (GenericErrorsEnum.BAD_REQUEST.getCode().equals(String.valueOf(ex.status()))) {
                LOGGER.error("Postal code not found: {}", ex.getMessage());
                throw new RuntimeException("Postal code not found");
            }
            LOGGER.error("Error finding address by postal code: {}", ex.getMessage());
            throw new RuntimeException("Error finding address by postal code", ex);
        } catch (Exception ex) {
            LOGGER.error("Error finding address by postal code: {}", ex.getMessage());
            throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
        }
    }

}