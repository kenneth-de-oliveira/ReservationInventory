package io.bookwise.adapters.out.client.impl;

import com.example.inventorymanagement.BookResponse;
import com.example.inventorymanagement.SearchBookRequest;
import feign.FeignException;
import io.bookwise.adapters.out.client.BIMServiceClient;
import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.framework.errors.GenericErrorsEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManagementClientImpl implements InventoryManagementClient {

    private final BIMServiceClient serviceClient;

    @Override
    public BookResponse findByIsbn(SearchBookRequest request) {

        try {
            var response = serviceClient.findByIsbn(request);
            log.debug("Book found by isbn: {}", response);

            return response;
        } catch (FeignException ex) {
            if (ex.getMessage().contains("Book not found")) {
                this.handleMessageException(ex);
                throw new RuntimeException("Book not found");
            }
            this.handleMessageException(ex);
            throw new RuntimeException("Error finding Book by isbn: " + ex.getMessage());
        } catch (Exception ex) {
            this.handleMessageException(ex);
            throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
        }

    }

    private void handleMessageException(Exception ex) {
        log.error("Error finding Book by isbn: {}", ex.getMessage());
    }

}