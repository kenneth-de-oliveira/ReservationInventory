package io.bookwise.adapters.out.client.impl;

import com.example.inventorymanagement.*;
import feign.FeignException;
import io.bookwise.adapters.out.client.BIMServiceClient;
import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.client.dto.RetrieveAllBooksRequest;
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
                this.handleMessageException("Book not found: ", ex);
                throw new RuntimeException("Book not found");
            }
            this.handleMessageException("Error finding Book by isbn: ", ex);
            throw new RuntimeException("Error finding Book by isbn: " + ex.getMessage());
        } catch (Exception ex) {
            this.handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
            throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
        }

    }

    @Override
    public BookResponse findAll() {
        try {
            var request = new RetrieveAllBooksRequest();
            var response = serviceClient.findAll(request);
            log.info("Books found: {}", response);

            return response;
        } catch (FeignException ex) {
            if (ex.getMessage().contains("No books found")) {
                this.handleMessageException("No books found: ", ex);
                throw new RuntimeException("No books found");
            }
            this.handleMessageException("Error finding all books: ", ex);
            throw new RuntimeException("Error finding all books: " + ex.getMessage());
        } catch (Exception ex) {
            this.handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
            throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
        }
    }

    @Override
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        try {
            var response = serviceClient.createCategory(categoryRequest);
            log.info("Category created: {}", response);

            return response;
        } catch (FeignException ex) {
            this.handleMessageException("Error creating category: ", ex);
            throw new RuntimeException("Error creating category: " + ex.getMessage());
        } catch (Exception ex) {
            this.handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
            throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
        }
    }

    @Override
    public void saveBook(BookRequest bookRequest) {
        try {
            serviceClient.createBook(bookRequest);
            log.info("Book created: {}", bookRequest);

        } catch (FeignException ex) {
            if (ex.getMessage().contains("Category not found")) {
                this.handleMessageException("Category not found: ", ex);
                throw new RuntimeException("Category not found");
            }
            this.handleMessageException("Error creating book: ", ex);
            throw new RuntimeException("Error creating book: " + ex.getMessage());
        } catch (Exception ex) {
            this.handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
            throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
        }
    }

    private void handleMessageException(String msg, Exception ex) {
        log.error("{}: {}", msg, ex.getMessage());
    }

}